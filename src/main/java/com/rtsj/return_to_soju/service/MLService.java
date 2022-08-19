package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.dto.KakaoMLData;
import com.rtsj.return_to_soju.model.dto.request.KakaoMLDataSaveRequestDto;
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

import javax.persistence.NonUniqueResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Service
@Transactional
public class MLService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;
    private final DailySentenceRepository dailySentenceRepository;
    private final CalenderService calenderService;

    public void saveKakaoMLData(KakaoMLDataSaveRequestDto dto) {
        Long userId = dto.getUser_pk();
        List<KakaoMLData> datas = dto.getKakao_data();
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        saveDailySentence(user, datas);
        calenderRepository.saveCalenderEmotionCntByNatvieQuery(userId);
        calenderRepository.saveCalenderMainEmotionByNativeQuery(userId);
    }

    // 매번 calender를 찾는 쿼리문이 나감,,, 수정할 방법을 찾고싶은데 모르겠다..
    private void saveDailySentence(User user, List<KakaoMLData> datas) {
        datas.stream()
                .forEach(data -> {
                    String time = data.getDate_time();
                    LocalDateTime localDateTime = convertStringTimeToLocalDateTimeFormat(time);
                    LocalDate localDate = localDateTime.toLocalDate();

                    Calender calender = calenderService.findCalenderByUserAndLocalDate(user, localDate);

                    String sentence = data.getText();
                    Emotion emotion = data.getEmotion();
                    //todo Roomname, kakaoText 넣어줘야함
                    // 1. roomname 구현이 가능할진 모르겠음
                    // 2. kakaoText: 본문 파일을 알아야 당일 카톡보기를 구현햘 수 있음
                    // but 현재 이걸 어떻게 구현할지 논의해야
                    // 3. 사용자가 톡방을 한번 더 올렸을 때 중복검사 로직이 없음

                    DailySentence dailySentence = new DailySentence(calender, sentence, emotion);
                    dailySentenceRepository.save(dailySentence);
                });
    }



    private LocalDateTime convertStringTimeToLocalDateTimeFormat(String origin) {
        StringTokenizer st = new StringTokenizer(origin);

        String year = st.nextToken();
        String month = st.nextToken();
        String day = st.nextToken();
        String ampm = st.nextToken();
        String time = st.nextToken();

        year = year.substring(0, 4);
        year += "-";

        month = month.replaceAll("[^0-9]","");
        if (month.length() == 1) {
            month = "0" + month;
        }
        month += "-";

        day = day.replaceAll("일", "");
        if (day.length() == 1) {
            day = "0" + day;
        }

        boolean isAm = ampm.equals("오전");

        st = new StringTokenizer(time, ":");

        int intHour = Integer.parseInt(st.nextToken());

        if (intHour % 12 == 0) {
            intHour -= 12;
        }
        if (!isAm) {
            intHour += 12;
        }

        String hour = intHour + "";

        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        String min = st.nextToken();

        String convertString = year + month + day + " " + hour + ":" + min;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(convertString, formatter);
    }
}
