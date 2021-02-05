package com.target.exceptions;

import lombok.Data;

@Data
public class InvalidRequestException extends RuntimeException {

    String respCode;
    public InvalidRequestException(String string)
    {
        super(string);
        this.respCode = string;
    }
}
