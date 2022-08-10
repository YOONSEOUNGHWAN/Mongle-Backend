package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.exception.DuplicateSignInException;
import com.rtsj.return_to_soju.model.dto.dto.KakaoUserInfo;
import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.dto.response.LoginResponseDto;
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
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponseDto loginWithToken(String providerName, KakaoTokenDto kakaoTokenDto) {
        User user = getUserProfileByToken(providerName, kakaoTokenDto);
        String accessToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtProvider.createRefreshToken();
        return new LoginResponseDto(user, accessToken, refreshToken);
    }

    private Map<String, Object> getUserAttributesByToken(KakaoTokenDto kakaoTokenDto) throws WebClientResponseException {
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(kakaoTokenDto.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User getUserProfileByToken(String providerName, KakaoTokenDto kakaoTokenDto){
        if(!providerName.equals("kakao")){
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        Map<String, Object> userAttributesByToken = getUserAttributesByToken(kakaoTokenDto);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributesByToken);
        Long kakao_id = kakaoUserInfo.getId();
        String name = kakaoUserInfo.getName();
        String nickName = kakaoUserInfo.getNickName();
        if(userRepository.findById(kakao_id).isPresent()){
            throw new DuplicateSignInException();
        }
        User user = new User(kakao_id, name, nickName, kakaoTokenDto.getAccessToken(), kakaoTokenDto.getRefreshToken(), Role.ROLE_USER);
        User save = userRepository.save(user);
        return save;
    }

}

