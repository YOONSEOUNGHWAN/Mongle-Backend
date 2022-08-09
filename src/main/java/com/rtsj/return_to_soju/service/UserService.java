package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.common.config.oauth.service.KakaoAPI;
import com.rtsj.return_to_soju.model.dto.GetUserInfoByKakaoDto;
import com.rtsj.return_to_soju.model.dto.request.SignupUserDto;
import com.rtsj.return_to_soju.model.dto.response.BothTokenResponseDto;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Role;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    private final KakaoAPI kakaoAPI;

//    public void signup(RequestSignupDto dto){
//        String bcryptPwd = passwordEncoder.encode(dto.getPassword());
//        dto.setPassword(bcryptPwd);
//        User user = dto.toUserEntity();
//
//        userRepository.save(user);
//        return;
//    }


    //TODO : 회원가입 리팩토링
    // 리턴값이 우리(몽글)의 토큰이 되어야함
    @Transactional
    public BothTokenResponseDto signup(SignupUserDto dto) {
        String accessToken = dto.getAccessToken();
        String refreshToken = dto.getRefreshToken();

        GetUserInfoByKakaoDto kakaoUserInfo = kakaoAPI.getKakaoUserInfo(accessToken);
        Long id = kakaoUserInfo.getId();
        String nickname = kakaoUserInfo.getNickname();

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.updateKakaoName(nickname);
            user.updateKakaoTokens(accessToken, refreshToken);

            user.makeRole(Role.ROLE_USER);
            userRepository.save(user);

            return new BothTokenResponseDto(jwtProvider.createAccessToken(id), jwtProvider.createRefreshToken(id));

        } else {
            User user = new User(id, nickname);
            user.updateKakaoTokens(accessToken, refreshToken);

            user.makeRole(Role.ROLE_USER);
            userRepository.save(user);
            return new BothTokenResponseDto(jwtProvider.createAccessToken(id), jwtProvider.createRefreshToken(id));

        }
    }


}
