package com.rtsj.return_to_soju.model.dto.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoTokenDto {
    private String accessToken;
    private String refreshToken;
}