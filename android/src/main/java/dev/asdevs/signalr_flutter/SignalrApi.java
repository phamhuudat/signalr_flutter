// Autogenerated from Pigeon (v4.2.14), do not edit directly.
// See also: https://pub.dev/packages/pigeon

package dev.asdevs.signalr_flutter;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/** Generated class from Pigeon. */
@SuppressWarnings({"unused", "unchecked", "CodeBlock2Expr", "RedundantSuppression"})
public class SignalrApi {

  /** Transport method of the signalr connection. */
  public enum Transport {
    AUTO(0),
    SERVER_SENT_EVENTS(1),
    LONG_POLLING(2);

    private final int index;
    private Transport(final int index) {
      this.index = index;
    }
  }

  /** SignalR connection status */
  public enum ConnectionStatus {
    CONNECTING(0),
    CONNECTED(1),
    RECONNECTING(2),
    DISCONNECTED(3),
    CONNECTION_SLOW(4),
    CONNECTION_ERROR(5);

    private final int index;
    private ConnectionStatus(final int index) {
      this.index = index;
    }
  }

  /** Generated class from Pigeon that represents data sent in messages. */
  public static class ConnectionOptions {
    private @Nullable String baseUrl;
    public @Nullable String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(@Nullable String setterArg) {
      this.baseUrl = setterArg;
    }

    private @Nullable String hubName;
    public @Nullable String getHubName() { return hubName; }
    public void setHubName(@Nullable String setterArg) {
      this.hubName = setterArg;
    }

    private @Nullable String queryString;
    public @Nullable String getQueryString() { return queryString; }
    public void setQueryString(@Nullable String setterArg) {
      this.queryString = setterArg;
    }

    private @Nullable List<String> hubMethods;
    public @Nullable List<String> getHubMethods() { return hubMethods; }
    public void setHubMethods(@Nullable List<String> setterArg) {
      this.hubMethods = setterArg;
    }

    private @Nullable Map<String, String> headers;
    public @Nullable Map<String, String> getHeaders() { return headers; }
    public void setHeaders(@Nullable Map<String, String> setterArg) {
      this.headers = setterArg;
    }

    private @Nullable Transport transport;
    public @Nullable Transport getTransport() { return transport; }
    public void setTransport(@Nullable Transport setterArg) {
      this.transport = setterArg;
    }

    private @Nullable String connectionId;
    public @Nullable String getConnectionId() { return connectionId; }
    public void setConnectionId(@Nullable String setterArg) {
      this.connectionId = setterArg;
    }

    public static final class Builder {
      private @Nullable String baseUrl;
      public @NonNull Builder setBaseUrl(@Nullable String setterArg) {
        this.baseUrl = setterArg;
        return this;
      }
      private @Nullable String hubName;
      public @NonNull Builder setHubName(@Nullable String setterArg) {
        this.hubName = setterArg;
        return this;
      }
      private @Nullable String queryString;
      public @NonNull Builder setQueryString(@Nullable String setterArg) {
        this.queryString = setterArg;
        return this;
      }
      private @Nullable List<String> hubMethods;
      public @NonNull Builder setHubMethods(@Nullable List<String> setterArg) {
        this.hubMethods = setterArg;
        return this;
      }
      private @Nullable Map<String, String> headers;
      public @NonNull Builder setHeaders(@Nullable Map<String, String> setterArg) {
        this.headers = setterArg;
        return this;
      }
      private @Nullable Transport transport;
      public @NonNull Builder setTransport(@Nullable Transport setterArg) {
        this.transport = setterArg;
        return this;
      }
      private @Nullable String connectionId;
      public @NonNull Builder setConnectionId(@Nullable String setterArg) {
        this.connectionId = setterArg;
        return this;
      }
      public @NonNull ConnectionOptions build() {
        ConnectionOptions pigeonReturn = new ConnectionOptions();
        pigeonReturn.setBaseUrl(baseUrl);
        pigeonReturn.setHubName(hubName);
        pigeonReturn.setQueryString(queryString);
        pigeonReturn.setHubMethods(hubMethods);
        pigeonReturn.setHeaders(headers);
        pigeonReturn.setTransport(transport);
        pigeonReturn.setConnectionId(connectionId);
        return pigeonReturn;
      }
    }
    @NonNull ArrayList<Object> toList() {
      ArrayList<Object> toListResult = new ArrayList<Object>(7);
      toListResult.add(baseUrl);
      toListResult.add(hubName);
      toListResult.add(queryString);
      toListResult.add(hubMethods);
      toListResult.add(headers);
      toListResult.add(transport == null ? null : transport.index);
      toListResult.add(connectionId);
      return toListResult;
    }
    static @NonNull ConnectionOptions fromList(@NonNull ArrayList<Object> list) {
      ConnectionOptions pigeonResult = new ConnectionOptions();
      Object baseUrl = list.get(0);
      pigeonResult.setBaseUrl((String)baseUrl);
      Object hubName = list.get(1);
      pigeonResult.setHubName((String)hubName);
      Object queryString = list.get(2);
      pigeonResult.setQueryString((String)queryString);
      Object hubMethods = list.get(3);
      pigeonResult.setHubMethods((List<String>)hubMethods);
      Object headers = list.get(4);
      pigeonResult.setHeaders((Map<String, String>)headers);
      Object transport = list.get(5);
      pigeonResult.setTransport(transport == null ? null : Transport.values()[(int)transport]);
      Object connectionId = list.get(6);
      pigeonResult.setConnectionId((String)connectionId);
      return pigeonResult;
    }
  }

  /** Generated class from Pigeon that represents data sent in messages. */
  public static class StatusChangeResult {
    private @Nullable String connectionId;
    public @Nullable String getConnectionId() { return connectionId; }
    public void setConnectionId(@Nullable String setterArg) {
      this.connectionId = setterArg;
    }

    private @Nullable ConnectionStatus status;
    public @Nullable ConnectionStatus getStatus() { return status; }
    public void setStatus(@Nullable ConnectionStatus setterArg) {
      this.status = setterArg;
    }

    private @Nullable String errorMessage;
    public @Nullable String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(@Nullable String setterArg) {
      this.errorMessage = setterArg;
    }

    public static final class Builder {
      private @Nullable String connectionId;
      public @NonNull Builder setConnectionId(@Nullable String setterArg) {
        this.connectionId = setterArg;
        return this;
      }
      private @Nullable ConnectionStatus status;
      public @NonNull Builder setStatus(@Nullable ConnectionStatus setterArg) {
        this.status = setterArg;
        return this;
      }
      private @Nullable String errorMessage;
      public @NonNull Builder setErrorMessage(@Nullable String setterArg) {
        this.errorMessage = setterArg;
        return this;
      }
      public @NonNull StatusChangeResult build() {
        StatusChangeResult pigeonReturn = new StatusChangeResult();
        pigeonReturn.setConnectionId(connectionId);
        pigeonReturn.setStatus(status);
        pigeonReturn.setErrorMessage(errorMessage);
        return pigeonReturn;
      }
    }
    @NonNull ArrayList<Object> toList() {
      ArrayList<Object> toListResult = new ArrayList<Object>(3);
      toListResult.add(connectionId);
      toListResult.add(status == null ? null : status.index);
      toListResult.add(errorMessage);
      return toListResult;
    }
    static @NonNull StatusChangeResult fromList(@NonNull ArrayList<Object> list) {
      StatusChangeResult pigeonResult = new StatusChangeResult();
      Object connectionId = list.get(0);
      pigeonResult.setConnectionId((String)connectionId);
      Object status = list.get(1);
      pigeonResult.setStatus(status == null ? null : ConnectionStatus.values()[(int)status]);
      Object errorMessage = list.get(2);
      pigeonResult.setErrorMessage((String)errorMessage);
      return pigeonResult;
    }
  }

  public interface Result<T> {
    void success(T result);
    void error(Throwable error);
  }
  private static class SignalRHostApiCodec extends StandardMessageCodec {
    public static final SignalRHostApiCodec INSTANCE = new SignalRHostApiCodec();
    private SignalRHostApiCodec() {}
    @Override
    protected Object readValueOfType(byte type, @NonNull ByteBuffer buffer) {
      switch (type) {
        case (byte)128:         
          return ConnectionOptions.fromList((ArrayList<Object>) readValue(buffer));
        
        default:        
          return super.readValueOfType(type, buffer);
        
      }
    }
    @Override
    protected void writeValue(@NonNull ByteArrayOutputStream stream, Object value)     {
      if (value instanceof ConnectionOptions) {
        stream.write(128);
        writeValue(stream, ((ConnectionOptions) value).toList());
      } else 
{
        super.writeValue(stream, value);
      }
    }
  }

  /** Generated interface from Pigeon that represents a handler of messages from Flutter. */
  public interface SignalRHostApi {
    void connect(@NonNull ConnectionOptions connectionOptions, Result<String> result);
    void reconnect(@NonNull String connectionId, Result<String> result);
    void stop(@NonNull String connectionId, Result<Void> result);
    void isConnected(@NonNull String connectionId, Result<Boolean> result);
    void invokeMethod(@NonNull String methodName, @NonNull List<String> arguments, Result<String> result);

    /** The codec used by SignalRHostApi. */
    static MessageCodec<Object> getCodec() {
      return       SignalRHostApiCodec.INSTANCE;    }
    /**Sets up an instance of `SignalRHostApi` to handle messages through the `binaryMessenger`. */
    static void setup(BinaryMessenger binaryMessenger, SignalRHostApi api) {
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRHostApi.connect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            ArrayList wrapped = new ArrayList<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              assert args != null;
              ConnectionOptions connectionOptionsArg = (ConnectionOptions)args.get(0);
              if (connectionOptionsArg == null) {
                throw new NullPointerException("connectionOptionsArg unexpectedly null.");
              }
              Result<String> resultCallback = new Result<String>() {
                public void success(String result) {
                  wrapped.add(0, result);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  ArrayList<Object> wrappedError = wrapError(error);
                  reply.reply(wrappedError);
                }
              };

              api.connect(connectionOptionsArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              ArrayList<Object> wrappedError = wrapError(exception);
              reply.reply(wrappedError);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRHostApi.reconnect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            ArrayList wrapped = new ArrayList<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              assert args != null;
              String connectionIdArg = (String)args.get(0);
              if (connectionIdArg == null) {
                throw new NullPointerException("connectionIdArg unexpectedly null.");
              }
              Result<String> resultCallback = new Result<String>() {
                public void success(String result) {
                  wrapped.add(0, result);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  ArrayList<Object> wrappedError = wrapError(error);
                  reply.reply(wrappedError);
                }
              };

              api.reconnect(connectionIdArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              ArrayList<Object> wrappedError = wrapError(exception);
              reply.reply(wrappedError);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRHostApi.stop", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            ArrayList wrapped = new ArrayList<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              assert args != null;
              String connectionIdArg = (String)args.get(0);
              if (connectionIdArg == null) {
                throw new NullPointerException("connectionIdArg unexpectedly null.");
              }
              Result<Void> resultCallback = new Result<Void>() {
                public void success(Void result) {
                  wrapped.add(0, null);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  ArrayList<Object> wrappedError = wrapError(error);
                  reply.reply(wrappedError);
                }
              };

              api.stop(connectionIdArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              ArrayList<Object> wrappedError = wrapError(exception);
              reply.reply(wrappedError);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRHostApi.isConnected", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            ArrayList wrapped = new ArrayList<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              assert args != null;
              String connectionIdArg = (String)args.get(0);
              if (connectionIdArg == null) {
                throw new NullPointerException("connectionIdArg unexpectedly null.");
              }
              Result<Boolean> resultCallback = new Result<Boolean>() {
                public void success(Boolean result) {
                  wrapped.add(0, result);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  ArrayList<Object> wrappedError = wrapError(error);
                  reply.reply(wrappedError);
                }
              };

              api.isConnected(connectionIdArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              ArrayList<Object> wrappedError = wrapError(exception);
              reply.reply(wrappedError);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRHostApi.invokeMethod", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            ArrayList wrapped = new ArrayList<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              assert args != null;
              String methodNameArg = (String)args.get(0);
              if (methodNameArg == null) {
                throw new NullPointerException("methodNameArg unexpectedly null.");
              }
              List<String> argumentsArg = (List<String>)args.get(1);
              if (argumentsArg == null) {
                throw new NullPointerException("argumentsArg unexpectedly null.");
              }
              Result<String> resultCallback = new Result<String>() {
                public void success(String result) {
                  wrapped.add(0, result);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  ArrayList<Object> wrappedError = wrapError(error);
                  reply.reply(wrappedError);
                }
              };

              api.invokeMethod(methodNameArg, argumentsArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              ArrayList<Object> wrappedError = wrapError(exception);
              reply.reply(wrappedError);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
    }
  }
  private static class SignalRPlatformApiCodec extends StandardMessageCodec {
    public static final SignalRPlatformApiCodec INSTANCE = new SignalRPlatformApiCodec();
    private SignalRPlatformApiCodec() {}
    @Override
    protected Object readValueOfType(byte type, @NonNull ByteBuffer buffer) {
      switch (type) {
        case (byte)128:         
          return StatusChangeResult.fromList((ArrayList<Object>) readValue(buffer));
        
        default:        
          return super.readValueOfType(type, buffer);
        
      }
    }
    @Override
    protected void writeValue(@NonNull ByteArrayOutputStream stream, Object value)     {
      if (value instanceof StatusChangeResult) {
        stream.write(128);
        writeValue(stream, ((StatusChangeResult) value).toList());
      } else 
{
        super.writeValue(stream, value);
      }
    }
  }

  /** Generated class from Pigeon that represents Flutter messages that can be called from Java. */
  public static class SignalRPlatformApi {
    private final BinaryMessenger binaryMessenger;
    public SignalRPlatformApi(BinaryMessenger argBinaryMessenger){
      this.binaryMessenger = argBinaryMessenger;
    }
    public interface Reply<T> {
      void reply(T reply);
    }
    /** The codec used by SignalRPlatformApi. */
    static MessageCodec<Object> getCodec() {
      return       SignalRPlatformApiCodec.INSTANCE;
    }
    public void onStatusChange(@NonNull StatusChangeResult statusChangeResultArg, Reply<Void> callback) {
      BasicMessageChannel<Object> channel =
          new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRPlatformApi.onStatusChange."+statusChangeResultArg.connectionId, getCodec());
      channel.send(new ArrayList<Object>(Collections.singletonList(statusChangeResultArg)), channelReply -> {
        callback.reply(null);
      });
    }
    public void onNewMessage(@NonNull String hubNameArg, @NonNull String messageArg, @NonNull String connectionIdArg, Reply<Void> callback) {
      BasicMessageChannel<Object> channel =
          new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.SignalRPlatformApi.onNewMessage."+connectionIdArg, getCodec());
      channel.send(new ArrayList<Object>(Arrays.asList(hubNameArg, messageArg, connectionIdArg)), channelReply -> {
        callback.reply(null);
      });
    }
  }
  @NonNull private static ArrayList<Object> wrapError(@NonNull Throwable exception) {
    ArrayList<Object> errorList = new ArrayList<>(3);
    errorList.add(exception.toString());
    errorList.add(exception.getClass().getSimpleName());
    errorList.add("Cause: " + exception.getCause() + ", Stacktrace: " + Log.getStackTraceString(exception));
    return errorList;
  }
}
