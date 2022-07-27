package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Calender extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "calender_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Lob
    private String diary;

    @OneToMany(mappedBy = "calender")
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "calender")
    private List<DailySentence> dailySentenceList = new ArrayList<>();

    @OneToMany(mappedBy = "calender")
    private List<DailyTopic> topicList = new ArrayList<>();

    @OneToMany(mappedBy = "calender")
    private List<ImageFile> imageList = new ArrayList<>();

}
