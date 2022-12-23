package io.panshi.config.srv;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: ConfigSrv.proto")
public final class ConfigSrvGrpc {

  private ConfigSrvGrpc() {}

  public static final String SERVICE_NAME = "io.panshi.config.ConfigSrv";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.panshi.config.srv.CreateConfigRequest,
      io.panshi.config.srv.CreateConfigResponse> getCreateConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createConfig",
      requestType = io.panshi.config.srv.CreateConfigRequest.class,
      responseType = io.panshi.config.srv.CreateConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.panshi.config.srv.CreateConfigRequest,
      io.panshi.config.srv.CreateConfigResponse> getCreateConfigMethod() {
    io.grpc.MethodDescriptor<io.panshi.config.srv.CreateConfigRequest, io.panshi.config.srv.CreateConfigResponse> getCreateConfigMethod;
    if ((getCreateConfigMethod = ConfigSrvGrpc.getCreateConfigMethod) == null) {
      synchronized (ConfigSrvGrpc.class) {
        if ((getCreateConfigMethod = ConfigSrvGrpc.getCreateConfigMethod) == null) {
          ConfigSrvGrpc.getCreateConfigMethod = getCreateConfigMethod = 
              io.grpc.MethodDescriptor.<io.panshi.config.srv.CreateConfigRequest, io.panshi.config.srv.CreateConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.panshi.config.ConfigSrv", "createConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.CreateConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.CreateConfigResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ConfigSrvMethodDescriptorSupplier("createConfig"))
                  .build();
          }
        }
     }
     return getCreateConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.panshi.config.srv.RegisterInstanceRequest,
      io.panshi.config.srv.RegisterInstanceResponse> getRegisterClientInstanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerClientInstance",
      requestType = io.panshi.config.srv.RegisterInstanceRequest.class,
      responseType = io.panshi.config.srv.RegisterInstanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.panshi.config.srv.RegisterInstanceRequest,
      io.panshi.config.srv.RegisterInstanceResponse> getRegisterClientInstanceMethod() {
    io.grpc.MethodDescriptor<io.panshi.config.srv.RegisterInstanceRequest, io.panshi.config.srv.RegisterInstanceResponse> getRegisterClientInstanceMethod;
    if ((getRegisterClientInstanceMethod = ConfigSrvGrpc.getRegisterClientInstanceMethod) == null) {
      synchronized (ConfigSrvGrpc.class) {
        if ((getRegisterClientInstanceMethod = ConfigSrvGrpc.getRegisterClientInstanceMethod) == null) {
          ConfigSrvGrpc.getRegisterClientInstanceMethod = getRegisterClientInstanceMethod = 
              io.grpc.MethodDescriptor.<io.panshi.config.srv.RegisterInstanceRequest, io.panshi.config.srv.RegisterInstanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.panshi.config.ConfigSrv", "registerClientInstance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.RegisterInstanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.RegisterInstanceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ConfigSrvMethodDescriptorSupplier("registerClientInstance"))
                  .build();
          }
        }
     }
     return getRegisterClientInstanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.panshi.config.srv.ListConfigRequest,
      io.panshi.config.srv.ListConfigResponse> getListConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listConfig",
      requestType = io.panshi.config.srv.ListConfigRequest.class,
      responseType = io.panshi.config.srv.ListConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.panshi.config.srv.ListConfigRequest,
      io.panshi.config.srv.ListConfigResponse> getListConfigMethod() {
    io.grpc.MethodDescriptor<io.panshi.config.srv.ListConfigRequest, io.panshi.config.srv.ListConfigResponse> getListConfigMethod;
    if ((getListConfigMethod = ConfigSrvGrpc.getListConfigMethod) == null) {
      synchronized (ConfigSrvGrpc.class) {
        if ((getListConfigMethod = ConfigSrvGrpc.getListConfigMethod) == null) {
          ConfigSrvGrpc.getListConfigMethod = getListConfigMethod = 
              io.grpc.MethodDescriptor.<io.panshi.config.srv.ListConfigRequest, io.panshi.config.srv.ListConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.panshi.config.ConfigSrv", "listConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.ListConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.ListConfigResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ConfigSrvMethodDescriptorSupplier("listConfig"))
                  .build();
          }
        }
     }
     return getListConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConfigSrvStub newStub(io.grpc.Channel channel) {
    return new ConfigSrvStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConfigSrvBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ConfigSrvBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConfigSrvFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ConfigSrvFutureStub(channel);
  }

  /**
   */
  public static abstract class ConfigSrvImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 创建配置信息
     * </pre>
     */
    public void createConfig(io.panshi.config.srv.CreateConfigRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.CreateConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateConfigMethod(), responseObserver);
    }

    /**
     * <pre>
     * 注册服务实例
     * </pre>
     */
    public void registerClientInstance(io.panshi.config.srv.RegisterInstanceRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.RegisterInstanceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterClientInstanceMethod(), responseObserver);
    }

    /**
     * <pre>
     * 拉取配置
     * </pre>
     */
    public void listConfig(io.panshi.config.srv.ListConfigRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.ListConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConfigMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateConfigMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.panshi.config.srv.CreateConfigRequest,
                io.panshi.config.srv.CreateConfigResponse>(
                  this, METHODID_CREATE_CONFIG)))
          .addMethod(
            getRegisterClientInstanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.panshi.config.srv.RegisterInstanceRequest,
                io.panshi.config.srv.RegisterInstanceResponse>(
                  this, METHODID_REGISTER_CLIENT_INSTANCE)))
          .addMethod(
            getListConfigMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.panshi.config.srv.ListConfigRequest,
                io.panshi.config.srv.ListConfigResponse>(
                  this, METHODID_LIST_CONFIG)))
          .build();
    }
  }

  /**
   */
  public static final class ConfigSrvStub extends io.grpc.stub.AbstractStub<ConfigSrvStub> {
    private ConfigSrvStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigSrvStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigSrvStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigSrvStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建配置信息
     * </pre>
     */
    public void createConfig(io.panshi.config.srv.CreateConfigRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.CreateConfigResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 注册服务实例
     * </pre>
     */
    public void registerClientInstance(io.panshi.config.srv.RegisterInstanceRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.RegisterInstanceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterClientInstanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 拉取配置
     * </pre>
     */
    public void listConfig(io.panshi.config.srv.ListConfigRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.ListConfigResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConfigSrvBlockingStub extends io.grpc.stub.AbstractStub<ConfigSrvBlockingStub> {
    private ConfigSrvBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigSrvBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigSrvBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigSrvBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建配置信息
     * </pre>
     */
    public io.panshi.config.srv.CreateConfigResponse createConfig(io.panshi.config.srv.CreateConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateConfigMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 注册服务实例
     * </pre>
     */
    public io.panshi.config.srv.RegisterInstanceResponse registerClientInstance(io.panshi.config.srv.RegisterInstanceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRegisterClientInstanceMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 拉取配置
     * </pre>
     */
    public io.panshi.config.srv.ListConfigResponse listConfig(io.panshi.config.srv.ListConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConfigSrvFutureStub extends io.grpc.stub.AbstractStub<ConfigSrvFutureStub> {
    private ConfigSrvFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigSrvFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigSrvFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigSrvFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建配置信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.panshi.config.srv.CreateConfigResponse> createConfig(
        io.panshi.config.srv.CreateConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateConfigMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 注册服务实例
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.panshi.config.srv.RegisterInstanceResponse> registerClientInstance(
        io.panshi.config.srv.RegisterInstanceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterClientInstanceMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 拉取配置
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.panshi.config.srv.ListConfigResponse> listConfig(
        io.panshi.config.srv.ListConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CONFIG = 0;
  private static final int METHODID_REGISTER_CLIENT_INSTANCE = 1;
  private static final int METHODID_LIST_CONFIG = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConfigSrvImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConfigSrvImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_CONFIG:
          serviceImpl.createConfig((io.panshi.config.srv.CreateConfigRequest) request,
              (io.grpc.stub.StreamObserver<io.panshi.config.srv.CreateConfigResponse>) responseObserver);
          break;
        case METHODID_REGISTER_CLIENT_INSTANCE:
          serviceImpl.registerClientInstance((io.panshi.config.srv.RegisterInstanceRequest) request,
              (io.grpc.stub.StreamObserver<io.panshi.config.srv.RegisterInstanceResponse>) responseObserver);
          break;
        case METHODID_LIST_CONFIG:
          serviceImpl.listConfig((io.panshi.config.srv.ListConfigRequest) request,
              (io.grpc.stub.StreamObserver<io.panshi.config.srv.ListConfigResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ConfigSrvBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConfigSrvBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.panshi.config.srv.ConfigSrvOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConfigSrv");
    }
  }

  private static final class ConfigSrvFileDescriptorSupplier
      extends ConfigSrvBaseDescriptorSupplier {
    ConfigSrvFileDescriptorSupplier() {}
  }

  private static final class ConfigSrvMethodDescriptorSupplier
      extends ConfigSrvBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConfigSrvMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConfigSrvGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConfigSrvFileDescriptorSupplier())
              .addMethod(getCreateConfigMethod())
              .addMethod(getRegisterClientInstanceMethod())
              .addMethod(getListConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
