package com.rtsj.return_to_soju.model.dto.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToken {
    private String accessToken;
    private String refreshToken;
}