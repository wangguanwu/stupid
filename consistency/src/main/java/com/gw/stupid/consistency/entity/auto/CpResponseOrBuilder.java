// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package com.gw.stupid.consistency.entity.auto;

public interface CpResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CpResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes data = 1;</code>
   */
  com.google.protobuf.ByteString getData();

  /**
   * <code>string errMsg = 2;</code>
   */
  java.lang.String getErrMsg();
  /**
   * <code>string errMsg = 2;</code>
   */
  com.google.protobuf.ByteString
      getErrMsgBytes();

  /**
   * <code>bool success = 3;</code>
   */
  boolean getSuccess();
}