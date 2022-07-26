package com.rtsj.return_to_soju.common.config.auth;
import com.rtsj.return_to_soju.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

//시큐리티가 주소요청이 오면 -> 로그인을 진행함
//로그인이 진행 되면 session or JWT을 만들어서 반환해야한다.
//시큐리티가 가지고 있는 session에 들어가야하는 object는 정의되어 있음 => Authentication 타입의 객체
//그 객체 안에는 유저 정보를 등록해야한다.
//User오브젝트의 타입은 UserDetails타입의 객체이다.

//Security Session -> Authentication -> UserDetails type
//이렇게 접근 권한을 설정해야함
//나중에 메모리에서 띄워 줄거임.


public class PrincipalDetails implements UserDetails {

    private User user; // 콜포지션

    public  PrincipalDetails(User user){
        this.user = user;
    }

    //해당 유저의 권한을 return하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //1년 동안 로그인을 안하면 휴면 계정으로 전환하기로 했다면,,
        //user.getLoginDate(); -> 현재시간 - 로그인 시간 => 1년 초과하면 Return false
        return true;
    }
}
