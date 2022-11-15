package io.panshi.grpc.etcd.api.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class PanshiException extends Exception {
    private ErrorCode errorCode;
    private String message;

    public PanshiException(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public static PanshiException newError(ErrorCode errorCode, String message){
        return new PanshiException(errorCode,message);
    }

    public String formatMessage(){
        return String.format("code=%s,message=%s",errorCode.name(), message);
    }
}
