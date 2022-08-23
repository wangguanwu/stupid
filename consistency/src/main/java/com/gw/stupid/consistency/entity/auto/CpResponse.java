// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package com.gw.stupid.consistency.entity.auto;

/**
 * Protobuf type {@code CpResponse}
 */
public  final class CpResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:CpResponse)
    CpResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CpResponse.newBuilder() to construct.
  private CpResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CpResponse() {
    data_ = com.google.protobuf.ByteString.EMPTY;
    errMsg_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new CpResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private CpResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
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

            data_ = input.readBytes();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            errMsg_ = s;
            break;
          }
          case 24: {

            success_ = input.readBool();
            break;
          }
          default: {
            if (!parseUnknownField(
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
    return com.gw.stupid.consistency.entity.auto.Consistency.internal_static_CpResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.gw.stupid.consistency.entity.auto.Consistency.internal_static_CpResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.gw.stupid.consistency.entity.auto.CpResponse.class, com.gw.stupid.consistency.entity.auto.CpResponse.Builder.class);
  }

  public static final int DATA_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString data_;
  /**
   * <code>bytes data = 1;</code>
   */
  public com.google.protobuf.ByteString getData() {
    return data_;
  }

  public static final int ERRMSG_FIELD_NUMBER = 2;
  private volatile java.lang.Object errMsg_;
  /**
   * <code>string errMsg = 2;</code>
   */
  public java.lang.String getErrMsg() {
    java.lang.Object ref = errMsg_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      errMsg_ = s;
      return s;
    }
  }
  /**
   * <code>string errMsg = 2;</code>
   */
  public com.google.protobuf.ByteString
      getErrMsgBytes() {
    java.lang.Object ref = errMsg_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      errMsg_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SUCCESS_FIELD_NUMBER = 3;
  private boolean success_;
  /**
   * <code>bool success = 3;</code>
   */
  public boolean getSuccess() {
    return success_;
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
    if (!data_.isEmpty()) {
      output.writeBytes(1, data_);
    }
    if (!getErrMsgBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, errMsg_);
    }
    if (success_ != false) {
      output.writeBool(3, success_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!data_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, data_);
    }
    if (!getErrMsgBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, errMsg_);
    }
    if (success_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, success_);
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
    if (!(obj instanceof com.gw.stupid.consistency.entity.auto.CpResponse)) {
      return super.equals(obj);
    }
    com.gw.stupid.consistency.entity.auto.CpResponse other = (com.gw.stupid.consistency.entity.auto.CpResponse) obj;

    if (!getData()
        .equals(other.getData())) return false;
    if (!getErrMsg()
        .equals(other.getErrMsg())) return false;
    if (getSuccess()
        != other.getSuccess()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + DATA_FIELD_NUMBER;
    hash = (53 * hash) + getData().hashCode();
    hash = (37 * hash) + ERRMSG_FIELD_NUMBER;
    hash = (53 * hash) + getErrMsg().hashCode();
    hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getSuccess());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.gw.stupid.consistency.entity.auto.CpResponse parseFrom(
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
  public static Builder newBuilder(com.gw.stupid.consistency.entity.auto.CpResponse prototype) {
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
   * Protobuf type {@code CpResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:CpResponse)
      com.gw.stupid.consistency.entity.auto.CpResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.gw.stupid.consistency.entity.auto.Consistency.internal_static_CpResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.gw.stupid.consistency.entity.auto.Consistency.internal_static_CpResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.gw.stupid.consistency.entity.auto.CpResponse.class, com.gw.stupid.consistency.entity.auto.CpResponse.Builder.class);
    }

    // Construct using com.gw.stupid.consistency.entity.generator.CpResponse.newBuilder()
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
      data_ = com.google.protobuf.ByteString.EMPTY;

      errMsg_ = "";

      success_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.gw.stupid.consistency.entity.auto.Consistency.internal_static_CpResponse_descriptor;
    }

    @java.lang.Override
    public com.gw.stupid.consistency.entity.auto.CpResponse getDefaultInstanceForType() {
      return com.gw.stupid.consistency.entity.auto.CpResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.gw.stupid.consistency.entity.auto.CpResponse build() {
      com.gw.stupid.consistency.entity.auto.CpResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.gw.stupid.consistency.entity.auto.CpResponse buildPartial() {
      com.gw.stupid.consistency.entity.auto.CpResponse result = new com.gw.stupid.consistency.entity.auto.CpResponse(this);
      result.data_ = data_;
      result.errMsg_ = errMsg_;
      result.success_ = success_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.gw.stupid.consistency.entity.auto.CpResponse) {
        return mergeFrom((com.gw.stupid.consistency.entity.auto.CpResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.gw.stupid.consistency.entity.auto.CpResponse other) {
      if (other == com.gw.stupid.consistency.entity.auto.CpResponse.getDefaultInstance()) return this;
      if (other.getData() != com.google.protobuf.ByteString.EMPTY) {
        setData(other.getData());
      }
      if (!other.getErrMsg().isEmpty()) {
        errMsg_ = other.errMsg_;
        onChanged();
      }
      if (other.getSuccess() != false) {
        setSuccess(other.getSuccess());
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
      com.gw.stupid.consistency.entity.auto.CpResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.gw.stupid.consistency.entity.auto.CpResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes data = 1;</code>
     */
    public com.google.protobuf.ByteString getData() {
      return data_;
    }
    /**
     * <code>bytes data = 1;</code>
     */
    public Builder setData(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      data_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes data = 1;</code>
     */
    public Builder clearData() {
      
      data_ = getDefaultInstance().getData();
      onChanged();
      return this;
    }

    private java.lang.Object errMsg_ = "";
    /**
     * <code>string errMsg = 2;</code>
     */
    public java.lang.String getErrMsg() {
      java.lang.Object ref = errMsg_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        errMsg_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public com.google.protobuf.ByteString
        getErrMsgBytes() {
      java.lang.Object ref = errMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        errMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder setErrMsg(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      errMsg_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder clearErrMsg() {
      
      errMsg_ = getDefaultInstance().getErrMsg();
      onChanged();
      return this;
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder setErrMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      errMsg_ = value;
      onChanged();
      return this;
    }

    private boolean success_ ;
    /**
     * <code>bool success = 3;</code>
     */
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 3;</code>
     */
    public Builder setSuccess(boolean value) {
      
      success_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool success = 3;</code>
     */
    public Builder clearSuccess() {
      
      success_ = false;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:CpResponse)
  }

  // @@protoc_insertion_point(class_scope:CpResponse)
  private static final com.gw.stupid.consistency.entity.auto.CpResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.gw.stupid.consistency.entity.auto.CpResponse();
  }

  public static com.gw.stupid.consistency.entity.auto.CpResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CpResponse>
      PARSER = new com.google.protobuf.AbstractParser<CpResponse>() {
    @java.lang.Override
    public CpResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new CpResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CpResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CpResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.gw.stupid.consistency.entity.auto.CpResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
