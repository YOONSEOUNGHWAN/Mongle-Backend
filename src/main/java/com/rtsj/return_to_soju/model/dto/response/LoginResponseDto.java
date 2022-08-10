package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    @Schema(description = "사용자 이름", nullable = true, example = "윤승환")
    private String name;
    @Schema(description = "Mongle Access Token", example = "aldksfja;esoifjaslkdfaeafsdvsaer")
    private String accessToken;
    @Schema(description = "Mongle Refresh Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    private String refreshToken;

    public LoginResponseDto(User user, String accessToken, String refreshToken){
        this.name = user.getName();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}