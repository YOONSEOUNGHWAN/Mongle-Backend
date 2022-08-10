package com.rtsj.return_to_soju.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BothTokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
