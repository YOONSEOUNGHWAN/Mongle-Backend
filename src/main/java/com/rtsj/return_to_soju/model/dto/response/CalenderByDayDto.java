package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.dto.EmotionWithPercentDto;
import com.rtsj.return_to_soju.model.dto.ScheduleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalenderByDayDto {

    private List<String> imageList = new ArrayList<>();
    private String diary;
    private List<ScheduleDto> scheduleList = new ArrayList<>();
    private List<EmotionWithPercentDto> emotionList = new ArrayList<>();
}
