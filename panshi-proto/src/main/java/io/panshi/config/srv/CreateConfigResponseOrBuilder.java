// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfigSrvDto.proto

package io.panshi.config.srv;

public interface CreateConfigResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:io.panshi.config.srv.CreateConfigResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.io.panshi.config.srv.CommonResp cRsp = 1;</code>
   */
  boolean hasCRsp();
  /**
   * <code>.io.panshi.config.srv.CommonResp cRsp = 1;</code>
   */
  io.panshi.config.srv.CommonResp getCRsp();
  /**
   * <code>.io.panshi.config.srv.CommonResp cRsp = 1;</code>
   */
  io.panshi.config.srv.CommonRespOrBuilder getCRspOrBuilder();

  /**
   * <code>string id = 2;</code>
   */
  java.lang.String getId();
  /**
   * <code>string id = 2;</code>
   */
  com.google.protobuf.ByteString
      getIdBytes();
}