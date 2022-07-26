package com.rtsj.return_to_soju.common.config.oauth.controller;

import com.rtsj.return_to_soju.common.config.oauth.service.KakaoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final KakaoAPI kakaoAPI;

    @GetMapping("/kakao")
    public String kakaoCallback(@RequestParam String code){
        System.out.println("code = " + code);
        String accessToken = kakaoAPI.getAccessToken(code);
        kakaoAPI.createKakaoUser(accessToken);
        kakaoAPI.unlinkKakaoUser(accessToken);
        return code;
    }



}
