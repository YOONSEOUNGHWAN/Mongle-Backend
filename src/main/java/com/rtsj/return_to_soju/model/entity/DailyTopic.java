package com.rtsj.return_to_soju.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class DailyTopic extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "topic_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    @Column(name = "topic_name")
    private String name;
}
