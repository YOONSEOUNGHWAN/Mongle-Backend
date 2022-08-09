package com.rtsj.return_to_soju.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    public void createAccessTokenTest(){
        String accessToken = jwtProvider.createAccessToken(1L);
        String refreshToken = jwtProvider.createRefreshToken(1L);
        Long userIdByAccessToken = Long.parseLong(jwtProvider.getUserId(accessToken));
        Long userIdByRefreshToken = Long.parseLong(jwtProvider.getUserId(refreshToken));

        assertThat(userIdByAccessToken).isEqualTo(1L);
        assertThat(userIdByRefreshToken).isEqualTo(1L);
    }
}