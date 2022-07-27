package com.rtsj.return_to_soju.common.config.auth;

import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//시큐리티 설정에서
//
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //시큐리티 session = authentication = userdetails타입이 들어온다..
    //return되면 authentication(userDetails)
    //session(authentication)으로 등록된다.. //이렇게 권한을 확인할 수 있음.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        if(optionalUser.isPresent()){
            return new PrincipalDetails(optionalUser.orElseThrow(() -> new UsernameNotFoundException("not found")));
        }
        return null;
    }
}
