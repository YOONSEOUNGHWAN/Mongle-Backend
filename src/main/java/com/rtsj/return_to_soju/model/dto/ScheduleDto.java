package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.entity.Schedule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ScheduleDto(Schedule schedule){
        this.name = schedule.getName();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
    }

}
