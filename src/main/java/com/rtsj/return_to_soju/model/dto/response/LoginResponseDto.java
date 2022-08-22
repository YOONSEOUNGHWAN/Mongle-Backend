package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 및 로그인 응답")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@NoArgsConstructor
public class LoginResponseDto {
    @Schema(description = "사용자 이름", nullable = true, example = "윤승환")
    private String name;
    @Schema(description = "Mongle Access Token", example = "aldksfja;esoifjaslkdfaeafsdvsaer")
    private String accessToken;
    @Schema(description = "Mongle Refresh Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    private String refreshToken;

    @Schema(description = "새로운 회원인지 여부, true면 새로운 회원", example = "true")
    private Boolean isNew;

}