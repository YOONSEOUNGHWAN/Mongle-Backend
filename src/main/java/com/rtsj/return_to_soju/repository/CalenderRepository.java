package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    // TODO: 2022/08/13 해당 연 월 기준으로 유저정보에 따른 날짜, 감정 가져오는 jpql 작성하기

}
