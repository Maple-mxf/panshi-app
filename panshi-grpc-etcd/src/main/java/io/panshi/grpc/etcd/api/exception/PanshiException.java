package io.panshi.grpc.etcd.api.exception;

import lombok.Data;

@Data
public class PanshiException extends Exception {
    private ErrorCode errorCode;
    private String message;
}
