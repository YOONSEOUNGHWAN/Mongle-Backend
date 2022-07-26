package com.rtsj.return_to_soju.domain.user.entity.dto;

import com.rtsj.return_to_soju.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestSignupDto{

    private String userName;

    private String kakaoName;

    private String password;

    public User toUserEntity(){
        return new User(userName, kakaoName, password);
    }
}
