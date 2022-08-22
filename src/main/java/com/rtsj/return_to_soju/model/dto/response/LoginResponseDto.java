package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 및 로그인 응답")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class LoginResponseDto {

    @Schema(description = "사용자 kakao id값", example = "2374316219")
    private Long id;
    @Schema(description = "사용자 카카오 닉네임", nullable = true, example = "윤승환")
    private String kakaoName;
    @Schema(description = "Mongle xAccess Token", example = "aldksfja;esoifjaslkdfaeafsdvsaer")
    private String accessToken;
    @Schema(description = "Mongle Refresh Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    private String refreshToken;

    @Schema(description = "새로운 회원인지 여부, true면 새로운 회원", example = "true")
    private Boolean isNew;

    public LoginResponseDto(User user, Boolean isNew) {
        this.id = user.getId();
        this.kakaoName = user.getKakaoName();
        this.isNew = isNew;
    }


}