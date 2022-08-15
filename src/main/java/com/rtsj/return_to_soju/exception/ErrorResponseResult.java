package com.rtsj.return_to_soju.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "에답 정보")
@Data
public class ErrorResponseResult implements ResponseResult{
    @Schema(example = "해당 에러에 대한 내용이 나옵니다.")
    private final String message;
    public ErrorResponseResult(String message){
        this.message = message;
    }
    public ErrorResponseResult(Exception e){
        this(e.getMessage());
    }
    @Schema(example = "false")
    @Override
    public boolean isSuccessful() {
        return false;
    }
}
