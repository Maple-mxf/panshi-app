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

  public static final String SERVICE_NAME = "io.panshi.config.srv.ConfigSrv";

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
                  "io.panshi.config.srv.ConfigSrv", "createConfig"))
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

  private static volatile io.grpc.MethodDescriptor<io.panshi.config.srv.DescribeConfigListRequest,
      io.panshi.config.srv.DescribeConfigListResponse> getDescribeConfigListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "describeConfigList",
      requestType = io.panshi.config.srv.DescribeConfigListRequest.class,
      responseType = io.panshi.config.srv.DescribeConfigListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.panshi.config.srv.DescribeConfigListRequest,
      io.panshi.config.srv.DescribeConfigListResponse> getDescribeConfigListMethod() {
    io.grpc.MethodDescriptor<io.panshi.config.srv.DescribeConfigListRequest, io.panshi.config.srv.DescribeConfigListResponse> getDescribeConfigListMethod;
    if ((getDescribeConfigListMethod = ConfigSrvGrpc.getDescribeConfigListMethod) == null) {
      synchronized (ConfigSrvGrpc.class) {
        if ((getDescribeConfigListMethod = ConfigSrvGrpc.getDescribeConfigListMethod) == null) {
          ConfigSrvGrpc.getDescribeConfigListMethod = getDescribeConfigListMethod = 
              io.grpc.MethodDescriptor.<io.panshi.config.srv.DescribeConfigListRequest, io.panshi.config.srv.DescribeConfigListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.panshi.config.srv.ConfigSrv", "describeConfigList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.DescribeConfigListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.panshi.config.srv.DescribeConfigListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ConfigSrvMethodDescriptorSupplier("describeConfigList"))
                  .build();
          }
        }
     }
     return getDescribeConfigListMethod;
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
     * 查询配置列表
     * </pre>
     */
    public void describeConfigList(io.panshi.config.srv.DescribeConfigListRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.DescribeConfigListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDescribeConfigListMethod(), responseObserver);
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
            getDescribeConfigListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.panshi.config.srv.DescribeConfigListRequest,
                io.panshi.config.srv.DescribeConfigListResponse>(
                  this, METHODID_DESCRIBE_CONFIG_LIST)))
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
     * 查询配置列表
     * </pre>
     */
    public void describeConfigList(io.panshi.config.srv.DescribeConfigListRequest request,
        io.grpc.stub.StreamObserver<io.panshi.config.srv.DescribeConfigListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDescribeConfigListMethod(), getCallOptions()), request, responseObserver);
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
     * 查询配置列表
     * </pre>
     */
    public io.panshi.config.srv.DescribeConfigListResponse describeConfigList(io.panshi.config.srv.DescribeConfigListRequest request) {
      return blockingUnaryCall(
          getChannel(), getDescribeConfigListMethod(), getCallOptions(), request);
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
     * 查询配置列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.panshi.config.srv.DescribeConfigListResponse> describeConfigList(
        io.panshi.config.srv.DescribeConfigListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDescribeConfigListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CONFIG = 0;
  private static final int METHODID_DESCRIBE_CONFIG_LIST = 1;

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
        case METHODID_DESCRIBE_CONFIG_LIST:
          serviceImpl.describeConfigList((io.panshi.config.srv.DescribeConfigListRequest) request,
              (io.grpc.stub.StreamObserver<io.panshi.config.srv.DescribeConfigListResponse>) responseObserver);
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
              .addMethod(getDescribeConfigListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
