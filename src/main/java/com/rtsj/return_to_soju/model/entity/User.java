package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.enums.Role;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RTUSER")
@Getter
@DynamicUpdate //상속 받는다고 어노테이션까지 상속은 안되더라...
public class User extends BaseEntity{
    @Id
    @Column(name = "user_id")
    private Long id; //katalk PK 값으로 매핑하기
    private String name;
    private String nickName;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private String cloudEmail;
    private String calenderEmail;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "kakao_update")
    private LocalDateTime kakaoUpdate;
    @OneToMany(mappedBy = "user")
    private List<Calender> calenderList = new ArrayList<>();

    public User(Long id, String name, String nickName, String kakaoAccessToken, String kakaoRefreshToken,  Role role){
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
        this.role = role;
    }


    public void setKakaoUpdate(){
        this.kakaoUpdate = LocalDateTime.now();
    }
    public void updateNickName(String nickName){
        this.nickName = nickName;
    }
    public void updateKakaoRefreshToken(String kakaoRefreshToken){
        this.kakaoRefreshToken = kakaoRefreshToken;
    }

    public void updateKakaoAccessToken(String kakaoAccessToken){
        this.kakaoAccessToken = kakaoAccessToken;
    }

}
