package com.vorono4ka.swf.exceptions;

public class NegativeTagLengthException extends LoadingFaultException {
    public NegativeTagLengthException(String message) {
        super(message);
    }
}
