package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "1주 단위로 총 4주의 감정 점수와 총 감정 갯수를 나타내는 dto")
@Data
public class EmotionScoreByMonthDto {

    List<EmotionScoreWithWeekDto> scoreList = new ArrayList<>();

    private int happy = 0;
    private int neutral = 0;
    private int angry = 0;
    private int anxious = 0;
    private int tired = 0;
    private int sad = 0;

    public EmotionScoreByMonthDto(List<EmotionCntWithDate> emotionCntWithDateList) {
        CalendarUtil calendarUtil = new CalendarUtil();

    }
}
