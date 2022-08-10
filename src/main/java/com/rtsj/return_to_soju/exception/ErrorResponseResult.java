package com.rtsj.return_to_soju.exception;

import lombok.Data;

@Data
public class ErrorResponseResult implements ResponseResult{
    private final String message;

    public ErrorResponseResult(String message){
        this.message = message;
    }
    public ErrorResponseResult(Exception e){
        this(e.getMessage());
    }
    @Override
    public boolean isSuccessful() {
        return false;
    }
}
