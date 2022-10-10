package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.exception.InvalidWeekException;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByMonthDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByWeekDto;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final CalenderRepository calenderRepository;

    private final UserRepository userRepository;
    private final CalendarUtil calendarUtil;

    public EmotionScoreByWeekDto getWeekStatistics(Long userId, int year, int month, int week) {
        LocalDate[] startEndDate = calendarUtil.findWeekWithYearAndMonth(year, month, week);
        LocalDate startDate = startEndDate[0];
        LocalDate endDate = startEndDate[1];

        log.info(userId + "유저 " + startDate + "부터 " + endDate + "까지의 감정 점수 가져오기");
        List<EmotionCntWithDate> emotionCntWithDateList = calenderRepository.getEmotionStatisticsWithPeriod(userId, startDate, endDate);

        return new EmotionScoreByWeekDto(emotionCntWithDateList);

    }

    public EmotionScoreByMonthDto getMonthStatistics(Long userId, Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, calendarUtil.getMonthEndDay(year, month));

        List<EmotionCntWithDate> emotionStatisticsWithPeriod = calenderRepository.getEmotionStatisticsWithPeriod(userId, startDate, endDate);
//        calendarUtil.
        return new EmotionScoreByMonthDto(emotionStatisticsWithPeriod);
    }



}
