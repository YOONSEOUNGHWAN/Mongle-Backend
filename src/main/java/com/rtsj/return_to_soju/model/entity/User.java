package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.enums.Role;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(name = "RTUser")
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String kakaoName;

    private String phone;

    private String kakaoAccessToken;

    private String kakaoRefreshToken;

    @Column(name = "email_cloud")
    private String cloudEmail;

    @Enumerated(EnumType.STRING)
    private Role role; // ROLE_USER, ROLE_ADMIN

    @Column(name = "email_calendar")
    private String calendarEmail;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "user")
    private List<Calender> calenderList = new ArrayList<>();

    public User(Long id, String name, String kakaoName) {
        this.id = id;
        this.name = name;
        this.kakaoName = kakaoName;
    }

    public void updateKakaoName(String kakaoName) {
        this.kakaoName = kakaoName;
    }

    public void updateKakaoTokens(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
    }
}
