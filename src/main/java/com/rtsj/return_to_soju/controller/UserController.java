package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.RequestSignupDto;
import com.rtsj.return_to_soju.model.dto.request.SignupUserDto;
import com.rtsj.return_to_soju.model.dto.response.BothTokenResponseDto;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping("/test")
//    public String test(){
//        return "test";
//    }
//
//    @PostMapping("/join")
//    public String join(@RequestBody RequestSignupDto dto){
//        userService.signup(dto);
//        return "회원가입완료";
//    }

    @Secured("ROLE_USER")
    @GetMapping("/test")
    public String test(){
        return "pass";
    }

    @PostMapping("/users")
    public BothTokenResponseDto join(@RequestBody SignupUserDto dto) {
        BothTokenResponseDto response = userService.signup(dto);

        return response;
    }


}
