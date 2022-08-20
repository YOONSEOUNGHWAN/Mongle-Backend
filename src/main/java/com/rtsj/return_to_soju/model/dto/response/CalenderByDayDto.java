package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.dto.EmotionWithPercentDto;
import com.rtsj.return_to_soju.model.dto.ScheduleDto;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CalenderByDayDto {

    private List<String> imageList = new ArrayList<>();
    private String diary;
    private List<ScheduleDto> scheduleList = new ArrayList<>();
    private List<EmotionWithPercentDto> emotionList = new ArrayList<>();

    public CalenderByDayDto(Calender calender){
        this.imageList = calender.getImageList().stream()
                .map(imageFile -> imageFile.getUrl())
                .collect(Collectors.toList());
        this.diary = calender.getDiary();
        this.scheduleList = calender.getScheduleList().stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
        for(int i=0; i<Emotion.values().length; i++){
            this.emotionList.add(new EmotionWithPercentDto(Emotion.values()[i], (int) (calender.getEmotionPercent(i)*100)));
            System.out.println("Emotion.values()[i] = " + Emotion.values()[i]);
            System.out.println("calender.getEmotionPercent(i) = " + calender.getEmotionPercent(i));
        }
    }
}
