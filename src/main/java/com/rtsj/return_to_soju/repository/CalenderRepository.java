package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    Optional<Calender> findByUserAndDate(User user, LocalDate date);
    List<Calender> findALLByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    @Modifying
    @Query(value = "" +
            "update calender c " +
            "set c.angry = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('ANGRY')), " +
            "c.anxios = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('ANXIOS')), " +
            "c.happy = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('HAPPY')), " +
            "c.neutral = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('NEUTRAL')), " +
            "c.sad = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('SAD')), " +
            "c.tired = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('TIRED')) " +
            "where c.calender_id in " +
            "(select id from " +
            "(select c2.calender_id as id " +
            "from rtuser left join calender c2 on rtuser.user_id = c2.user_id " +
            "where rtuser.user_id=:userId) as t)",
            nativeQuery = true)
    int saveCalenderEmotionCntByNatvieQuery(@Param("userId") Long userId);
}
