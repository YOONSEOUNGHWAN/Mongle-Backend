package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestSignupDto{

    private String userName;

    private String kakaoName;

    private String password;


}
