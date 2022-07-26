package com.rtsj.return_to_soju.domain.user.service;

import com.rtsj.return_to_soju.domain.user.entity.User;
import com.rtsj.return_to_soju.domain.user.entity.dto.RequestSignupDto;
import com.rtsj.return_to_soju.domain.user.repository.UserRepository;
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
