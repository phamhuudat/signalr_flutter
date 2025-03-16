import 'dart:async';
import 'dart:math';

import 'package:flutter/foundation.dart';
import 'package:signalr_flutter/signalr_api.dart';
import 'package:signalr_flutter/signalr_platform_interface.dart';
import 'package:uuid/uuid.dart';

class SignalR extends SignalrPlatformInterface implements SignalRPlatformApi {
  // Private variables
  static final SignalRHostApi _signalrApi = SignalRHostApi();

  // Constructor
  SignalR(
    String baseUrl,
    String hubName, {
    String? queryString,
    Map<String, String>? headers,
    List<String>? hubMethods,
    Transport transport = Transport.auto,
    void Function(ConnectionStatus?)? statusChangeCallback,
    void Function(String, String)? hubCallback,
  }) : super(
          baseUrl,
          hubName,
          queryString: queryString,
          headers: headers,
          hubMethods: hubMethods,
          statusChangeCallback: statusChangeCallback,
          hubCallback: hubCallback,
        );

  //---- Callback Methods ----//
  // ------------------------//
  @override
  Future<void> onNewMessage(String hubName, String message, String connectionId) async {
    hubCallback?.call(hubName, message);
  }

  @override
  Future<void> onStatusChange(StatusChangeResult statusChangeResult) async {
    connectionId = statusChangeResult.connectionId;

    statusChangeCallback?.call(statusChangeResult.status);

    if (statusChangeResult.errorMessage != null) {
      debugPrint('SignalR Error: ${statusChangeResult.errorMessage}');
    }
  }

  //---- Public Methods ----//
  // ------------------------//

  /// Connect to the SignalR Server with given [baseUrl] & [hubName].
  ///
  /// [queryString] is a optional field to send query to server.
  ///
  /// Returns the [connectionId].
  @override
  Future<String?> connect() async {
    try {
      // Construct ConnectionOptions
      connectionId = const Uuid().v4();
      ConnectionOptions options = ConnectionOptions(
          baseUrl: baseUrl,
          hubName: hubName,
          queryString: queryString,
          hubMethods: hubMethods,
          headers: headers,
          transport: transport,
          connectionId: connectionId);
      // Register SignalR Callbacks
      SignalRPlatformApi.setup(this, connectedId: connectionId);
      await _signalrApi.connect(options);
      return connectionId;
    } catch (e) {
      return Future.error(e);
    }
  }

  /// Try to Reconnect SignalR connection if it gets disconnected.
  ///
  /// Returns the [connectionId]
  @override
  Future<String?> reconnect() async {
    try {
      connectionId = await _signalrApi.reconnect(connectionId ?? '');
      return connectionId;
    } catch (e) {
      return Future.error(e);
    }
  }

  /// Stops SignalR connection
  @override
  Future<void> stop() async {
    try {
      print('stop connect $connectionId');
      await _signalrApi.stop(connectionId ?? '');
    } catch (e) {
      return Future.error(e);
    }
  }

  /// Checks if SignalR connection is still active.
  ///
  /// Returns a boolean value
  @override
  Future<bool> isConnected() async {
    try {
      return await _signalrApi.isConnected(connectionId ?? '');
    } catch (e) {
      return Future.error(e);
    }
  }

  /// Invoke any server method with optional [arguments].
  @override
  Future<String> invokeMethod(String methodName, {List<String>? arguments}) async {
    try {
      return await _signalrApi.invokeMethod(methodName, arguments ?? List.empty());
    } catch (e) {
      return Future.error(e);
    }
  }
}
