package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.dto.RequestSignupDto;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public void signup(RequestSignupDto dto){
        String bcryptPwd = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(bcryptPwd);
        User user = dto.toUserEntity();

        userRepository.save(user);
        return;
    }
}
