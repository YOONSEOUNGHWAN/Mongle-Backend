package com.rtsj.return_to_soju.model.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoText extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "kakao_id")
    private Long id;

    @Column(name = "kakao_url")
    private String url;

    //OneToMany -> DailySentence 생략
}
