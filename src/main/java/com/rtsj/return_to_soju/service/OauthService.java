package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.JwtTokenProvider;
import com.rtsj.return_to_soju.exception.DuplicateSignInException;
import com.rtsj.return_to_soju.model.dto.common.KakaoUserInfo;
import com.rtsj.return_to_soju.model.dto.common.UserToken;
import com.rtsj.return_to_soju.model.dto.response.LoginResponse;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Role;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse loginWithToken(String providerName, UserToken userToken) {
        User user = getUserProfileByToken(providerName, userToken);
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();
        return new LoginResponse(user, BEARER_TYPE, accessToken, refreshToken);
    }

    private Map<String, Object> getUserAttributesByToken(UserToken userToken) throws WebClientResponseException {
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(userToken.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User getUserProfileByToken(String providerName, UserToken userToken){
        if(!providerName.equals("kakao")){
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        Map<String, Object> userAttributesByToken = getUserAttributesByToken(userToken);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributesByToken);
        Long kakao_id = kakaoUserInfo.getId();
        String name = kakaoUserInfo.getName();
        String nickName = kakaoUserInfo.getNickName();
        if(userRepository.findById(kakao_id).isPresent()){
            throw new DuplicateSignInException();
        }
        User user = new User(kakao_id, name, nickName, userToken.getAccessToken(), userToken.getRefreshToken(), Role.ROLE_USER);
        User save = userRepository.save(user);
        return save;
    }

}

