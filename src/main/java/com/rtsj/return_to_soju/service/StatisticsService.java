package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.InvalidWeekException;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
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

    public EmotionScoreByWeekDto getWeekStatistics(Long userId, int year, int month, int week) {
        int[] startEndDay = this.getStartEndDay(year, month, week);
        LocalDate startDate = LocalDate.of(year, month, startEndDay[0]);
        LocalDate endDate = LocalDate.of(year, month, startEndDay[1]);

        log.info(startDate +"부터 " + endDate +"까지의 감정 점수 가져오기");
        List<EmotionCntWithDate> emotionCntWithDateList = calenderRepository.getEmotionStatisticsWithPeriod(userId, startDate, endDate);
        return new EmotionScoreByWeekDto(emotionCntWithDateList);
    }

    private int[] getStartEndDay(int year, int month, int target) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);

        int[] ret = new int[2];

        for (int week = 1; week < cal.getMaximum(Calendar.WEEK_OF_MONTH); week++) {

            cal.set(Calendar.WEEK_OF_MONTH, week);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            int startDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            int endDay = cal.get(Calendar.DAY_OF_MONTH);

            if (week == 1 && startDay >= 7) {
                startDay = 1;
            }


            if (week == cal.getMaximum(Calendar.WEEK_OF_MONTH) - 1 && endDay <= 7) {
                cal.add(Calendar.MONTH,-1);
                endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            if (target == week) {
                ret[0] = startDay;
                ret[1] = endDay;
                return ret;
            }

        }

        throw new InvalidWeekException("적절하지 않은 주가 입력되었습니다. 최승용에게 연락 바랍니다");
    }

}
