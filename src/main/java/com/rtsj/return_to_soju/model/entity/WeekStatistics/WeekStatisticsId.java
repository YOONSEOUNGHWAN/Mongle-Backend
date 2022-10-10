package com.rtsj.return_to_soju.model.entity.WeekStatistics;

import com.rtsj.return_to_soju.model.entity.User;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class WeekStatisticsId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String yearWeek;
}
