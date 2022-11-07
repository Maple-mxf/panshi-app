// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfigSrvDto.proto

package io.panshi.config.srv;

/**
 * Protobuf type {@code io.panshi.config.DescribeConfigListRequest}
 */
public  final class DescribeConfigListRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.panshi.config.DescribeConfigListRequest)
    DescribeConfigListRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DescribeConfigListRequest.newBuilder() to construct.
  private DescribeConfigListRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DescribeConfigListRequest() {
    filterKeyword_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private DescribeConfigListRequest(
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

            filterKeyword_ = s;
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
    return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_DescribeConfigListRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_DescribeConfigListRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.panshi.config.srv.DescribeConfigListRequest.class, io.panshi.config.srv.DescribeConfigListRequest.Builder.class);
  }

  public static final int FILTERKEYWORD_FIELD_NUMBER = 1;
  private volatile java.lang.Object filterKeyword_;
  /**
   * <code>string filterKeyword = 1;</code>
   */
  public java.lang.String getFilterKeyword() {
    java.lang.Object ref = filterKeyword_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      filterKeyword_ = s;
      return s;
    }
  }
  /**
   * <code>string filterKeyword = 1;</code>
   */
  public com.google.protobuf.ByteString
      getFilterKeywordBytes() {
    java.lang.Object ref = filterKeyword_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      filterKeyword_ = b;
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
    if (!getFilterKeywordBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, filterKeyword_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getFilterKeywordBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, filterKeyword_);
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
    if (!(obj instanceof io.panshi.config.srv.DescribeConfigListRequest)) {
      return super.equals(obj);
    }
    io.panshi.config.srv.DescribeConfigListRequest other = (io.panshi.config.srv.DescribeConfigListRequest) obj;

    boolean result = true;
    result = result && getFilterKeyword()
        .equals(other.getFilterKeyword());
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
    hash = (37 * hash) + FILTERKEYWORD_FIELD_NUMBER;
    hash = (53 * hash) + getFilterKeyword().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.panshi.config.srv.DescribeConfigListRequest parseFrom(
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
  public static Builder newBuilder(io.panshi.config.srv.DescribeConfigListRequest prototype) {
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
   * Protobuf type {@code io.panshi.config.DescribeConfigListRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.panshi.config.DescribeConfigListRequest)
      io.panshi.config.srv.DescribeConfigListRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_DescribeConfigListRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_DescribeConfigListRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.panshi.config.srv.DescribeConfigListRequest.class, io.panshi.config.srv.DescribeConfigListRequest.Builder.class);
    }

    // Construct using io.panshi.config.srv.DescribeConfigListRequest.newBuilder()
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
      filterKeyword_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.panshi.config.srv.ConfigSrvDto.internal_static_io_panshi_config_DescribeConfigListRequest_descriptor;
    }

    @java.lang.Override
    public io.panshi.config.srv.DescribeConfigListRequest getDefaultInstanceForType() {
      return io.panshi.config.srv.DescribeConfigListRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.panshi.config.srv.DescribeConfigListRequest build() {
      io.panshi.config.srv.DescribeConfigListRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.panshi.config.srv.DescribeConfigListRequest buildPartial() {
      io.panshi.config.srv.DescribeConfigListRequest result = new io.panshi.config.srv.DescribeConfigListRequest(this);
      result.filterKeyword_ = filterKeyword_;
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
      if (other instanceof io.panshi.config.srv.DescribeConfigListRequest) {
        return mergeFrom((io.panshi.config.srv.DescribeConfigListRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.panshi.config.srv.DescribeConfigListRequest other) {
      if (other == io.panshi.config.srv.DescribeConfigListRequest.getDefaultInstance()) return this;
      if (!other.getFilterKeyword().isEmpty()) {
        filterKeyword_ = other.filterKeyword_;
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
      io.panshi.config.srv.DescribeConfigListRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.panshi.config.srv.DescribeConfigListRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object filterKeyword_ = "";
    /**
     * <code>string filterKeyword = 1;</code>
     */
    public java.lang.String getFilterKeyword() {
      java.lang.Object ref = filterKeyword_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        filterKeyword_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string filterKeyword = 1;</code>
     */
    public com.google.protobuf.ByteString
        getFilterKeywordBytes() {
      java.lang.Object ref = filterKeyword_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        filterKeyword_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string filterKeyword = 1;</code>
     */
    public Builder setFilterKeyword(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      filterKeyword_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string filterKeyword = 1;</code>
     */
    public Builder clearFilterKeyword() {
      
      filterKeyword_ = getDefaultInstance().getFilterKeyword();
      onChanged();
      return this;
    }
    /**
     * <code>string filterKeyword = 1;</code>
     */
    public Builder setFilterKeywordBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      filterKeyword_ = value;
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


    // @@protoc_insertion_point(builder_scope:io.panshi.config.DescribeConfigListRequest)
  }

  // @@protoc_insertion_point(class_scope:io.panshi.config.DescribeConfigListRequest)
  private static final io.panshi.config.srv.DescribeConfigListRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.panshi.config.srv.DescribeConfigListRequest();
  }

  public static io.panshi.config.srv.DescribeConfigListRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DescribeConfigListRequest>
      PARSER = new com.google.protobuf.AbstractParser<DescribeConfigListRequest>() {
    @java.lang.Override
    public DescribeConfigListRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new DescribeConfigListRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<DescribeConfigListRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DescribeConfigListRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.panshi.config.srv.DescribeConfigListRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

