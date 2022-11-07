package io.panshi.config.agent;

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
 * <pre>
 *  rpc push
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: ConfigAgent.proto")
public final class ConfigAgentGrpc {

  private ConfigAgentGrpc() {}

  public static final String SERVICE_NAME = "io.panshi.config.ConfigAgent";

  // Static method descriptors that strictly reflect the proto.
  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConfigAgentStub newStub(io.grpc.Channel channel) {
    return new ConfigAgentStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConfigAgentBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ConfigAgentBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConfigAgentFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ConfigAgentFutureStub(channel);
  }

  /**
   * <pre>
   *  rpc push
   * </pre>
   */
  public static abstract class ConfigAgentImplBase implements io.grpc.BindableService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .build();
    }
  }

  /**
   * <pre>
   *  rpc push
   * </pre>
   */
  public static final class ConfigAgentStub extends io.grpc.stub.AbstractStub<ConfigAgentStub> {
    private ConfigAgentStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigAgentStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigAgentStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigAgentStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   *  rpc push
   * </pre>
   */
  public static final class ConfigAgentBlockingStub extends io.grpc.stub.AbstractStub<ConfigAgentBlockingStub> {
    private ConfigAgentBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigAgentBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigAgentBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigAgentBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   *  rpc push
   * </pre>
   */
  public static final class ConfigAgentFutureStub extends io.grpc.stub.AbstractStub<ConfigAgentFutureStub> {
    private ConfigAgentFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConfigAgentFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConfigAgentFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConfigAgentFutureStub(channel, callOptions);
    }
  }


  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConfigAgentImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConfigAgentImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
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

  private static abstract class ConfigAgentBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConfigAgentBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.panshi.config.agent.ConfigAgentOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConfigAgent");
    }
  }

  private static final class ConfigAgentFileDescriptorSupplier
      extends ConfigAgentBaseDescriptorSupplier {
    ConfigAgentFileDescriptorSupplier() {}
  }

  private static final class ConfigAgentMethodDescriptorSupplier
      extends ConfigAgentBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConfigAgentMethodDescriptorSupplier(String methodName) {
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
      synchronized (ConfigAgentGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConfigAgentFileDescriptorSupplier())
              .build();
        }
      }
    }
    return result;
  }
}
