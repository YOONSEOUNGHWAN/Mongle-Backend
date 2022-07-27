package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.RequestSignupDto;
import com.rtsj.return_to_soju.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/join")
    public String join(@RequestBody RequestSignupDto dto){
        userService.signup(dto);
        return "회원가입완료";
    }


}
