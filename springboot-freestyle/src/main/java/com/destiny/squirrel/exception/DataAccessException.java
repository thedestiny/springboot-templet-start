package com.destiny.squirrel.exception;

public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = -91618517067354291L;

    public DataAccessException() {}

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String msg, Exception ex) {
        super(msg,ex);
    }

}
