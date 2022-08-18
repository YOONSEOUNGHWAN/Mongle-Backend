package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.response.CalenderBetweenMonthResponseDto;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Transactional //User의 정보를 가져오는데 사용됨.. 추가로, UserRepository에서 캘린더를 빼오면 어떨까... 성능측면에서는 비슷해 보이는뎅..
    public List<CalenderBetweenMonthResponseDto> findEmotionBetweenMonth(Long userId, String year, String start, String end) {
        int parseYear = Integer.parseInt(year);
        int startMonth = Integer.parseInt(start);
        int endMonth = Integer.parseInt(end);
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        Calendar cal = Calendar.getInstance();
        cal.set(parseYear, endMonth -1, 1);
        List<CalenderBetweenMonthResponseDto> responseDtoList = calenderRepository.findALLByUserAndDateBetween(
                        user,
                        LocalDate.of(parseYear, startMonth, 1),
                        LocalDate.of(parseYear, endMonth, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                )
                .stream()
                .map(CalenderBetweenMonthResponseDto::new)
                .collect(Collectors.toList());
        return responseDtoList;
    }

    @Transactional
    public void createDiary(Long userId, String year, String month, String day, String diaryText) {
        String date = year + "-" + month + "-" + day;
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        Calender calender = findCalenderByUserAndLocalDate(user, localDate);

        calender.writeOrUpdateDiary(diaryText);
        calenderRepository.save(calender);
    }

    public Calender findCalenderByUserAndLocalDate(User user, LocalDate localDate) {
        Optional<Calender> optionalCalender = calenderRepository.findByUserAndDate(user, localDate);

        if (optionalCalender.isEmpty()) {
            Calender calender = new Calender(user, localDate);
            return calenderRepository.save(calender);
        }
        return optionalCalender.get();
    }
}
