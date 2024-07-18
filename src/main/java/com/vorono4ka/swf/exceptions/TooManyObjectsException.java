package com.vorono4ka.swf.exceptions;

public class TooManyObjectsException extends LoadingFaultException {
    public TooManyObjectsException(String message) {
        super(message);
    }
}
