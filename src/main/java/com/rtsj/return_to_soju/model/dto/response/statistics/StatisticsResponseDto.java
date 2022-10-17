package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsResponseDto {
    Float[] scoreList;
    String startDate;
    private int happy = 0;
    private int neutral = 0;
    private int angry = 0;
    private int anxious = 0;
    private int tired = 0;
    private int sad = 0;

    public StatisticsResponseDto(List<WeekStatistics> result, int year, int month, int startWeek, int lastWeek) {
        CalendarUtil calendarUtil = new CalendarUtil();

        int totalWeek = lastWeek - startWeek + 1;
        scoreList = new Float[totalWeek];

        startDate = calendarUtil.getFirstDateWithYearMonth(year, month).toString();

        for (WeekStatistics weekStatistics : result) {
            int yearWeek = getWeek(weekStatistics.getId().getYearWeek());
            int monthWeek = calendarUtil.getMonthWeekWithYearWeek(year,yearWeek);

            scoreList[monthWeek-1] = weekStatistics.getScore();

            happy += weekStatistics.getHappy();
            neutral += weekStatistics.getNeutral();
            angry += weekStatistics.getAngry();
            anxious += weekStatistics.getAnxious();
            tired += weekStatistics.getTired();
            sad += weekStatistics.getSad();
        }
    }
    private int getWeek(String yearWeek){
        String[] token = yearWeek.split("/");
        return Integer.parseInt(token[1]);
    }
}
