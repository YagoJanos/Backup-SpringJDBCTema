package com.ilegra.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DBException extends RuntimeException{

    public DBException(String msg){
        super(msg);
    }

    public DBException(String msg, Throwable e){
        super(msg, e);
    }
}
