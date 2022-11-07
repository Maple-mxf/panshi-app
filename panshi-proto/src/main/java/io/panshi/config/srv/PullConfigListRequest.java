// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfigSrvDto.proto

package io.panshi.config.srv;

/**
 * Protobuf type {@code io.panshi.config.PullConfigListRequest}
 */
public  final class PullConfigListRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.panshi.config.PullConfigListRequest)
    PullConfigListRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use PullConfigListRequest.newBuilder() to construct.
  private PullConfigListRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PullConfigListRequest() {
    group_ = "";
    set_ = "";
    currentVersion_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private PullConfigListRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            group_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            set_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            currentVersion_ = s;
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_PullConfigListRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_PullConfigListRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.panshi.config.srv.PullConfigListRequest.class, io.panshi.config.srv.PullConfigListRequest.Builder.class);
  }

  public static final int GROUP_FIELD_NUMBER = 1;
  private volatile java.lang.Object group_;
  /**
   * <code>string group = 1;</code>
   */
  public java.lang.String getGroup() {
    java.lang.Object ref = group_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      group_ = s;
      return s;
    }
  }
  /**
   * <code>string group = 1;</code>
   */
  public com.google.protobuf.ByteString
      getGroupBytes() {
    java.lang.Object ref = group_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      group_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SET_FIELD_NUMBER = 2;
  private volatile java.lang.Object set_;
  /**
   * <code>string set = 2;</code>
   */
  public java.lang.String getSet() {
    java.lang.Object ref = set_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      set_ = s;
      return s;
    }
  }
  /**
   * <code>string set = 2;</code>
   */
  public com.google.protobuf.ByteString
      getSetBytes() {
    java.lang.Object ref = set_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      set_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CURRENTVERSION_FIELD_NUMBER = 3;
  private volatile java.lang.Object currentVersion_;
  /**
   * <code>string currentVersion = 3;</code>
   */
  public java.lang.String getCurrentVersion() {
    java.lang.Object ref = currentVersion_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      currentVersion_ = s;
      return s;
    }
  }
  /**
   * <code>string currentVersion = 3;</code>
   */
  public com.google.protobuf.ByteString
      getCurrentVersionBytes() {
    java.lang.Object ref = currentVersion_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      currentVersion_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getGroupBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, group_);
    }
    if (!getSetBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, set_);
    }
    if (!getCurrentVersionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, currentVersion_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getGroupBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, group_);
    }
    if (!getSetBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, set_);
    }
    if (!getCurrentVersionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, currentVersion_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.panshi.config.srv.PullConfigListRequest)) {
      return super.equals(obj);
    }
    io.panshi.config.srv.PullConfigListRequest other = (io.panshi.config.srv.PullConfigListRequest) obj;

    boolean result = true;
    result = result && getGroup()
        .equals(other.getGroup());
    result = result && getSet()
        .equals(other.getSet());
    result = result && getCurrentVersion()
        .equals(other.getCurrentVersion());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + GROUP_FIELD_NUMBER;
    hash = (53 * hash) + getGroup().hashCode();
    hash = (37 * hash) + SET_FIELD_NUMBER;
    hash = (53 * hash) + getSet().hashCode();
    hash = (37 * hash) + CURRENTVERSION_FIELD_NUMBER;
    hash = (53 * hash) + getCurrentVersion().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.PullConfigListRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.panshi.config.srv.PullConfigListRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code io.panshi.config.PullConfigListRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.panshi.config.PullConfigListRequest)
      io.panshi.config.srv.PullConfigListRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_PullConfigListRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_PullConfigListRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.panshi.config.srv.PullConfigListRequest.class, io.panshi.config.srv.PullConfigListRequest.Builder.class);
    }

    // Construct using io.panshi.config.srv.PullConfigListRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      group_ = "";

      set_ = "";

      currentVersion_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_PullConfigListRequest_descriptor;
    }

    @java.lang.Override
    public io.panshi.config.srv.PullConfigListRequest getDefaultInstanceForType() {
      return io.panshi.config.srv.PullConfigListRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.panshi.config.srv.PullConfigListRequest build() {
      io.panshi.config.srv.PullConfigListRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.panshi.config.srv.PullConfigListRequest buildPartial() {
      io.panshi.config.srv.PullConfigListRequest result = new io.panshi.config.srv.PullConfigListRequest(this);
      result.group_ = group_;
      result.set_ = set_;
      result.currentVersion_ = currentVersion_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.panshi.config.srv.PullConfigListRequest) {
        return mergeFrom((io.panshi.config.srv.PullConfigListRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.panshi.config.srv.PullConfigListRequest other) {
      if (other == io.panshi.config.srv.PullConfigListRequest.getDefaultInstance()) return this;
      if (!other.getGroup().isEmpty()) {
        group_ = other.group_;
        onChanged();
      }
      if (!other.getSet().isEmpty()) {
        set_ = other.set_;
        onChanged();
      }
      if (!other.getCurrentVersion().isEmpty()) {
        currentVersion_ = other.currentVersion_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      io.panshi.config.srv.PullConfigListRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.panshi.config.srv.PullConfigListRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object group_ = "";
    /**
     * <code>string group = 1;</code>
     */
    public java.lang.String getGroup() {
      java.lang.Object ref = group_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        group_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string group = 1;</code>
     */
    public com.google.protobuf.ByteString
        getGroupBytes() {
      java.lang.Object ref = group_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        group_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string group = 1;</code>
     */
    public Builder setGroup(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      group_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string group = 1;</code>
     */
    public Builder clearGroup() {
      
      group_ = getDefaultInstance().getGroup();
      onChanged();
      return this;
    }
    /**
     * <code>string group = 1;</code>
     */
    public Builder setGroupBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      group_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object set_ = "";
    /**
     * <code>string set = 2;</code>
     */
    public java.lang.String getSet() {
      java.lang.Object ref = set_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        set_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string set = 2;</code>
     */
    public com.google.protobuf.ByteString
        getSetBytes() {
      java.lang.Object ref = set_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        set_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string set = 2;</code>
     */
    public Builder setSet(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      set_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string set = 2;</code>
     */
    public Builder clearSet() {
      
      set_ = getDefaultInstance().getSet();
      onChanged();
      return this;
    }
    /**
     * <code>string set = 2;</code>
     */
    public Builder setSetBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      set_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object currentVersion_ = "";
    /**
     * <code>string currentVersion = 3;</code>
     */
    public java.lang.String getCurrentVersion() {
      java.lang.Object ref = currentVersion_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        currentVersion_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string currentVersion = 3;</code>
     */
    public com.google.protobuf.ByteString
        getCurrentVersionBytes() {
      java.lang.Object ref = currentVersion_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        currentVersion_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string currentVersion = 3;</code>
     */
    public Builder setCurrentVersion(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      currentVersion_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string currentVersion = 3;</code>
     */
    public Builder clearCurrentVersion() {
      
      currentVersion_ = getDefaultInstance().getCurrentVersion();
      onChanged();
      return this;
    }
    /**
     * <code>string currentVersion = 3;</code>
     */
    public Builder setCurrentVersionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      currentVersion_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:io.panshi.config.PullConfigListRequest)
  }

  // @@protoc_insertion_point(class_scope:io.panshi.config.PullConfigListRequest)
  private static final io.panshi.config.srv.PullConfigListRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.panshi.config.srv.PullConfigListRequest();
  }

  public static io.panshi.config.srv.PullConfigListRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PullConfigListRequest>
      PARSER = new com.google.protobuf.AbstractParser<PullConfigListRequest>() {
    @java.lang.Override
    public PullConfigListRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new PullConfigListRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PullConfigListRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PullConfigListRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.panshi.config.srv.PullConfigListRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

