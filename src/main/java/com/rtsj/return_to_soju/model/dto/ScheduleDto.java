package com.rtsj.return_to_soju.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
