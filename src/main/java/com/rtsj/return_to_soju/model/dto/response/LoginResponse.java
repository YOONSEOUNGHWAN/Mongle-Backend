package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    @Schema(description = "카카오PK", example = "23451")
    private Long id;
    @Schema(description = "사용자 이름", nullable = true, example = "윤승환")
    private String name;
    @Schema(description = "카카오 닉네임", example = "환이")
    private String nickName;
    @Schema(description = "권한",example = "ROLE_USER")
    private Role role;
    @Schema(description = "토큰타입", defaultValue = "Bearer", example = "Bearer")
    private String tokenType;
    @Schema(description = "Mongle Access Token", example = "aldksfja;esoifjaslkdfaeafsdvsaer")
    private String accessToken;
    @Schema(description = "Mongle Refresh Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    private String refreshToken;

    public LoginResponse(User user, String tokenType, String accessToken, String refreshToken){
        this.id = user.getId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.role = user.getRole();
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
}