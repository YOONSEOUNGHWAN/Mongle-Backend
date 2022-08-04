package com.rtsj.return_to_soju.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class DailySentence extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "sentence_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="kakao_id")
    private KakaoText kakaoText;

    private String sentence;
}
