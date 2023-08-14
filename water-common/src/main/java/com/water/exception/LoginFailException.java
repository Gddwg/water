package com.water.exception;

public class LoginFailException extends BaseException{
    public LoginFailException() {
    }

    public LoginFailException(String msg) {
        super(msg);
    }
}
