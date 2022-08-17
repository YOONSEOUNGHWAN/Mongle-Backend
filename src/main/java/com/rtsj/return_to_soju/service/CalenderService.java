package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.repository.CalenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {
    private final CalenderRepository calenderRepository;

    // TODO: 2022/08/13 몇 월 ~ 몇 월까지의 데이터 가져오는 서비스 만들기
    // TODO: 2022/08/13 query dsl 강의 듣기

}
