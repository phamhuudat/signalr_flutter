import Flutter
import UIKit

public class SwiftSignalrFlutterPlugin: NSObject, FlutterPlugin, FLTSignalRHostApi {
  private static var signalrApi : FLTSignalRPlatformApi?
  private var hub: Hub!
  private var connectionMap: [String: SignalR] = [:]

  public static func register(with registrar: FlutterPluginRegistrar) {
    let messenger : FlutterBinaryMessenger = registrar.messenger()
    let api : FLTSignalRHostApi & NSObjectProtocol = SwiftSignalrFlutterPlugin.init()
    FLTSignalRHostApiSetup(messenger, api)
    signalrApi = FLTSignalRPlatformApi.init(binaryMessenger: messenger)
  }

  public func detachFromEngine(for registrar: FlutterPluginRegistrar) {
    let messenger : FlutterBinaryMessenger = registrar.messenger()
    FLTSignalRHostApiSetup(messenger, nil)
    SwiftSignalrFlutterPlugin.signalrApi = nil
  }

  public func connect(_ connectionOptions: FLTConnectionOptions, completion: @escaping (String?, FlutterError?) -> Void) {
    let connection = SignalR(connectionOptions.baseUrl ?? "")
    connectionMap[connectionOptions.connectionId ?? ""] = connection
    if let queryString = connectionOptions.queryString, !queryString.isEmpty {
      let qs = queryString.components(separatedBy: "=")
      connection.queryString = [qs[0]:qs[1]]
    }

    switch connectionOptions.transport {
    case .longPolling:
      connection.transport = Transport.longPolling
    case .serverSentEvents:
      connection.transport = Transport.serverSentEvents
    case .auto:
      connection.transport = Transport.auto
    @unknown default:
      break
    }

    if let headers = connectionOptions.headers, !headers.isEmpty {
      connection.headers = headers
    }

    if let hubName = connectionOptions.hubName {
      hub = connection.createHubProxy(hubName)
    }

    if let hubMethods = connectionOptions.hubMethods, !hubMethods.isEmpty {
      hubMethods.forEach { (methodName) in
        hub.on(methodName) { (args) in
          let obj = args?[0]
          let data =  try! JSONSerialization.data(withJSONObject: obj, options: [])
          let json = String(data:data, encoding:.utf8)!
          SwiftSignalrFlutterPlugin.signalrApi?.onNewMessageHubName(methodName, message: json, connectionId: connectionOptions.connectionId ?? "", completion: { error in })
        }
      }
    }

    connection.starting = {
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.connecting
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.reconnecting = {
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.reconnecting
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.connected = { [weak self] in
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.connected
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.reconnected = { [weak self] in
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.connected
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.disconnected = { [weak self] in
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.disconnected
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.connectionSlow = { [weak self] in
      print("Connection slow...")
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.connectionSlow
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.error = { error in
      print("SignalR Error: \(error ?? [:])")
      let statusChangeResult : FLTStatusChangeResult = FLTStatusChangeResult.init()
      statusChangeResult.connectionId = connectionOptions.connectionId
      statusChangeResult.status = FLTConnectionStatus.connectionError
      statusChangeResult.errorMessage = error?.description
      SwiftSignalrFlutterPlugin.signalrApi?.onStatusChange(statusChangeResult, completion: { error in })
    }

    connection.start()
    completion(connectionOptions.connectionId ?? "", nil)
  }

  public func reconnectConnectionId(_ connectionId: String, completion: @escaping (String?, FlutterError?) -> Void) {
    if let connection = connectionMap[connectionId] {
      connection.start()
      completion(connectionId ?? "", nil)
    } else {
      completion(nil, FlutterError(code: "platform-error", message: "SignalR Connection not found or null", details: "Start SignalR connection first"))
    }
  }

  public func stopConnectionId(_ connectionId: String, completion: @escaping (FlutterError?) -> Void) {
    if let connection = connectionMap[connectionId] {
      connection.stop()
      connectionMap.removeValue(forKey: connectionId)
    } else {
      completion(FlutterError(code: "platform-error", message: "SignalR Connection not found or null", details: "Start SignalR connection first"))
    }
  }

  public func isConnectedConnectionId(_ connectionId: String, completion: @escaping (NSNumber?, FlutterError?) -> Void) {
    if let connection = connectionMap[connectionId] {
      switch connection.state {
      case .connected:
        completion(true, nil)
      default:
        completion(false, nil)
      }
    } else {
      completion(false, nil)
    }
  }

  public func invokeMethodMethodName(_ methodName: String, arguments: [String], completion: @escaping (String?, FlutterError?) -> Void) {
    do {
      if let hub = self.hub {
        try hub.invoke(methodName, arguments: arguments, callback: { (res, error) in
          if let error = error {
            completion(nil, FlutterError(code: "platform-error", message: String(describing: error), details: nil))
          } else {
            completion(res as? String ?? "", nil)
          }
        })
      } else {
        throw NSError.init(domain: "NullPointerException", code: 0, userInfo: [NSLocalizedDescriptionKey : "Hub is null. Initiate a connection first."])
      }
    } catch {
      completion(nil ,FlutterError.init(code: "platform-error", message: error.localizedDescription, details: nil))
    }
  }
}
