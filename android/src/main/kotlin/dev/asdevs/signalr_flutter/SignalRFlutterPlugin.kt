package dev.asdevs.signalr_flutter

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson

import io.flutter.embedding.engine.plugins.FlutterPlugin
import microsoft.aspnet.signalr.client.ConnectionState
import microsoft.aspnet.signalr.client.Credentials
import microsoft.aspnet.signalr.client.LogLevel
import microsoft.aspnet.signalr.client.SignalRFuture
import microsoft.aspnet.signalr.client.hubs.HubConnection
import microsoft.aspnet.signalr.client.hubs.HubProxy
import microsoft.aspnet.signalr.client.transport.LongPollingTransport
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport
import java.lang.Exception

/** SignalrFlutterPlugin */
class SignalrFlutterPlugin : FlutterPlugin, SignalrApi.SignalRHostApi {
//    private lateinit var connection: HubConnection
    private lateinit var hub: HubProxy
    private lateinit var signalrApi: SignalrApi.SignalRPlatformApi
    private val connectionMap = mutableMapOf<String, Any>()

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        SignalrApi.SignalRHostApi.setup(flutterPluginBinding.binaryMessenger, this)
        signalrApi = SignalrApi.SignalRPlatformApi(flutterPluginBinding.binaryMessenger)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        SignalrApi.SignalRHostApi.setup(binding.binaryMessenger, null)
    }

    override fun connect(
        connectionOptions: SignalrApi.ConnectionOptions,
        result: SignalrApi.Result<String>?
    ) {
        val connectedId = connectionOptions.connectionId.toString()
        try {
            val connection =
                if (connectionOptions.queryString?.isNotEmpty() == true) {
                    HubConnection(
                        connectionOptions.baseUrl,
                        connectionOptions.queryString,
                        true
                    ) { _: String, _: LogLevel ->
                    }
                } else {
                    HubConnection(connectionOptions.baseUrl)
                }

            if (connectionOptions.headers?.isNotEmpty() == true) {
                val cred = Credentials { request ->
                    request.headers = connectionOptions.headers
                }
                connection.credentials = cred
            }
            connectionMap[connectedId] = connection;
            hub = connection.createHubProxy(connectionOptions.hubName)

            connectionOptions.hubMethods?.forEach { methodName ->
                hub.on(methodName, { res ->
                    val gson = Gson()
                    val message: String = gson.toJson(res)
                    Handler(Looper.getMainLooper()).post {
                        signalrApi.onNewMessage(methodName, message, connectedId) { }
                    }
                }, Object::class.java)
            }

            connection.connected {
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.connectionId = connectedId
                    statusChangeResult.status = SignalrApi.ConnectionStatus.CONNECTED
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            connection.reconnected {
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.connectionId = connectedId
                    statusChangeResult.status = SignalrApi.ConnectionStatus.CONNECTED
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            connection.reconnecting {
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.connectionId = connectedId
                    statusChangeResult.status = SignalrApi.ConnectionStatus.RECONNECTING
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            connection.closed {
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.connectionId = connectedId
                    statusChangeResult.status = SignalrApi.ConnectionStatus.DISCONNECTED
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            connection.connectionSlow {
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.connectionId = connectedId
                    statusChangeResult.status = SignalrApi.ConnectionStatus.CONNECTION_SLOW
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            connection.error { handler ->
                Handler(Looper.getMainLooper()).post {
                    val statusChangeResult = SignalrApi.StatusChangeResult()
                    statusChangeResult.status = SignalrApi.ConnectionStatus.CONNECTION_ERROR
                    statusChangeResult.errorMessage = handler.localizedMessage
                    signalrApi.onStatusChange(statusChangeResult) { }
                }
            }

            when (connectionOptions.transport) {
                SignalrApi.Transport.SERVER_SENT_EVENTS -> connection.start(
                    ServerSentEventsTransport(
                        connection.logger
                    )
                )
                SignalrApi.Transport.LONG_POLLING -> connection.start(
                    LongPollingTransport(
                        connection.logger
                    )
                )
                else -> {
                    connection.start()
                }
            }

            result?.success(connectedId ?: "")
        } catch (ex: Exception) {
            result?.error(ex)
        }
    }

    override fun reconnect(connectedId: String, result: SignalrApi.Result<String>?) {
        try {
            val connection = connectionMap[connectedId] as HubConnection
            connection.start()
            result?.success(connectedId ?: "")
        } catch (ex: Exception) {
            result?.error(ex)
        }
    }

    override fun stop(connectedId: String, result: SignalrApi.Result<Void>?) {
        try {
            val connection = connectionMap[connectedId] as HubConnection
            connection.stop()
            connectionMap.remove(connectedId)
        } catch (ex: Exception) {
            result?.error(ex)
        }
    }

    override fun isConnected(connectedId: String,result: SignalrApi.Result<Boolean>?) {
        try {
            val connection = connectionMap[connectedId] as HubConnection
            when (connection.state) {
                ConnectionState.Connected -> result?.success(true)
                else -> result?.success(false)
            }
        } catch (ex: Exception) {
            result?.error(ex)
        }
    }

    override fun invokeMethod(
        methodName: String,
        arguments: MutableList<String>,
        result: SignalrApi.Result<String>?
    ) {
        try {
            val res: SignalRFuture<String> =
                hub.invoke(String::class.java, methodName, *arguments.toTypedArray())

            res.done { msg: String? ->
                Handler(Looper.getMainLooper()).post {
                    result?.success(msg ?: "")
                }
            }

            res.onError { throwable ->
                throw throwable
            }
        } catch (ex: Exception) {
            result?.error(ex)
        }
    }
}
