package com.rtsj.return_to_soju.common.auth;

import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
<<<<<<<< HEAD:src/main/java/com/rtsj/return_to_soju/common/auth/CustomJwtUserDetailsService.java
@Transactional
public class CustomJwtUserDetailsService implements UserDetailsService {
========
public class CustomUserDetailService implements UserDetailsService {
>>>>>>>> a71045bf7fd32086fc69aac5cbe5d57fa7cb9e86:src/main/java/com/rtsj/return_to_soju/common/auth/CustomUserDetailService.java

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
<<<<<<<< HEAD:src/main/java/com/rtsj/return_to_soju/common/auth/CustomJwtUserDetailsService.java
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        if(user.isPresent()){
            return new CustomJwtUserDetails(user.get());
========
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new CustomUserDetail(user);
>>>>>>>> a71045bf7fd32086fc69aac5cbe5d57fa7cb9e86:src/main/java/com/rtsj/return_to_soju/common/auth/CustomUserDetailService.java
        }
        else return null;
    }
}

