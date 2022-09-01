package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.response.CalenderBetweenMonthResponseDto;
import com.rtsj.return_to_soju.model.dto.response.CalenderByDayDto;
import com.rtsj.return_to_soju.model.dto.response.SentenceByEmotionWithDayDto;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.DailySentence;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Emotion;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.DailySentenceRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {
    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;
    private final DailySentenceRepository dailySentenceRepository;
    @Transactional //User의 정보를 가져오는데 사용됨.. 추가로, UserRepository에서 캘린더를 빼오면 어떨까... 성능측면에서는 비슷해 보이는뎅..
    public List<CalenderBetweenMonthResponseDto> getEmotionBetweenMonth(Long userId, String start, String end) {
        String[] startDate = start.split("-");
        String[] endDate = end.split("-");
        int startYear = Integer.parseInt(startDate[0]);
        int startMonth = Integer.parseInt(startDate[1]);
        int endYear = Integer.parseInt(endDate[0]);
        int endMonth = Integer.parseInt(endDate[1]);
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        Calendar cal = Calendar.getInstance();
        cal.set(endYear, endMonth -1, 1);
        List<CalenderBetweenMonthResponseDto> responseDtoList = calenderRepository.findALLByUserAndDateBetween(
                        user,
                        LocalDate.of(startYear, startMonth, 1),
                        LocalDate.of(endYear, endMonth, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                )
                .stream()
                .map(CalenderBetweenMonthResponseDto::new)
                .collect(Collectors.toList());
        return responseDtoList;
    }

    @Transactional
    public void createDiary(Long userId, String year, String month, String day, String diaryText) {
        LocalDate localDate = this.createLocalDateWithString(year, month, day);
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        Calender calender = findCalenderByUserAndLocalDate(user, localDate);

        calender.writeOrUpdateDiary(diaryText);
        calenderRepository.save(calender);
    }

    // 유저와 yyyy-mm-dd로 캘린더를 찾는 로직
    // if 캘린더가 없으면 만들어서 리턴
    public Calender findCalenderByUserAndLocalDate(User user, LocalDate localDate) {
        Optional<Calender> optionalCalender = calenderRepository.findByUserAndDate(user, localDate);

        if (optionalCalender.isEmpty()) {
            Calender calender = new Calender(user, localDate);
            return calenderRepository.save(calender);
        }
        return optionalCalender.get();
    }

    // year, month, day 가 인자로 들어왔을 때, LocalDate형식을 만들어서 반환
    private LocalDate createLocalDateWithString(String year, String month, String day) {
        String date = year + "-" + month + "-" + day;
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public List<SentenceByEmotionWithDayDto> getSentenceByEmotionWithDay(Long userId, String year, String month, String day, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        LocalDate localDate = this.createLocalDateWithString(year, month, day);
        Calender calender = this.findCalenderByUserAndLocalDate(user, localDate);

        List<DailySentence> byCalenderAndEmotion = dailySentenceRepository.findByCalenderAndEmotion(calender, emotion);
        return byCalenderAndEmotion.stream()
                .map(SentenceByEmotionWithDayDto::new)
                .collect(Collectors.toList());


    }

    @Transactional
    public CalenderByDayDto getDailyDataFromUser(Long userId, String year, String month, String day) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        LocalDate localDate = this.createLocalDateWithString(year, month, day);
        Calender calender = this.findCalenderByUserAndLocalDate(user, localDate);
        return new CalenderByDayDto(calender);
    }
}
