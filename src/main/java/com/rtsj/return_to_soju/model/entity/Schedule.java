package com.rtsj.return_to_soju.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calender_id")
    private Calender calender;

    @Column(name = "schedule_name")
    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
