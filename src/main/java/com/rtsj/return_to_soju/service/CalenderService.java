package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.response.CalenderBetweenMonthResponseDto;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {
    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;
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

}
