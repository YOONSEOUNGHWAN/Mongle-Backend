package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.dto.KakaoRenewInfo;
import com.rtsj.return_to_soju.model.dto.dto.KakaoUserInfo;
import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.dto.response.LoginResponseDto;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Role;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Value("${kakao.rest-api.key}")
    private String kakaoRestApiKey;

    @Transactional
    public LoginResponseDto loginWithKakaoToken(KakaoTokenDto kakaoTokenDto) {
        User user = createAndUpdateKakaoUserProfileWithToken(kakaoTokenDto);
        String accessToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtProvider.createRefreshToken();
        return new LoginResponseDto(user, accessToken, refreshToken);
    }

    private Map<String, Object> getKakaoUserAttributesWithToken(KakaoTokenDto kakaoTokenDto) throws WebClientResponseException {
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(kakaoTokenDto.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User createAndUpdateKakaoUserProfileWithToken(KakaoTokenDto kakaoTokenDto){
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfoWithToken(kakaoTokenDto);
        Long kakao_id = kakaoUserInfo.getId();
        String name = kakaoUserInfo.getName();
        String nickName = kakaoUserInfo.getNickName();
        Optional<User> optionalUser = userRepository.findById(kakao_id);
        if(optionalUser.isPresent()){
            User existUser = optionalUser.get();
            existUser.updateNickName(nickName);
            existUser.updateKakaoAccessToken(kakaoTokenDto.getAccessToken());
            existUser.updateKakaoRefreshToken(kakaoTokenDto.getRefreshToken());
            return existUser;
        }
        User user = new User(kakao_id, name, nickName, kakaoTokenDto.getAccessToken(), kakaoTokenDto.getRefreshToken(), Role.ROLE_USER);
        User save = userRepository.save(user);
        return save;
    }

    private KakaoUserInfo getKakaoUserInfoWithToken(KakaoTokenDto kakaoTokenDto) {
        Map<String, Object> userAttributesByToken = getKakaoUserAttributesWithToken(kakaoTokenDto);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributesByToken);
        return kakaoUserInfo;
    }

    public KakaoTokenDto renewKakaoToken(String kakaoRefreshToken){
        KakaoRenewInfo renewKakaoToken = getRenewKakaoToken(kakaoRefreshToken);
        String accessToken = renewKakaoToken.getAccessToken();
        String refreshToken = renewKakaoToken.getRefreshToken();
        KakaoTokenDto kakaoTokenDto = new KakaoTokenDto(accessToken, refreshToken);
        return kakaoTokenDto;
    }

    private KakaoRenewInfo getRenewKakaoToken(String kakaoRefreshToken){
        Map<String, Object> kakaoRenewResponse = getKakaoRenewResponse(kakaoRefreshToken);
        KakaoRenewInfo kakaoRenewInfo = new KakaoRenewInfo(kakaoRenewResponse);
        return kakaoRenewInfo;

    }

    private Map<String, Object> getKakaoRenewResponse(String kakaoRefreshToken){
        return WebClient.create()
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    httpHeaders.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(refreshTokenRequest(kakaoRefreshToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private MultiValueMap<String, String> refreshTokenRequest(String kakaoRefreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", kakaoRestApiKey);
        formData.add("refresh_token", kakaoRefreshToken);
        System.out.println(formData);
        return formData;
    }

}

