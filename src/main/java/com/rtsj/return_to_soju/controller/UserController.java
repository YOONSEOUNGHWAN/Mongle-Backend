package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.common.UserToken;
import com.rtsj.return_to_soju.model.dto.response.LoginResponse;
import com.rtsj.return_to_soju.service.OauthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "USER", description = "로그인 API")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final OauthService oauthService;
    @Operation(summary = "로그인 API", description = "카카오 access & refresh 토큰을 사용한 회원가입 및 로그인 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    @GetMapping("/login/{provider}")
    public ResponseEntity<LoginResponse> loginWithKakao(@Parameter(name = "provide", description = "플랫폼(kakao)", in = ParameterIn.PATH)
                                                        @PathVariable String provider,
                                                        @RequestBody UserToken userToken){
        LoginResponse loginResponse = oauthService.loginWithToken(provider, userToken);
        return ResponseEntity.ok().body(loginResponse);
    }

}
