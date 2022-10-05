package com.rtsj.return_to_soju.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.entity.QCalender;
import com.rtsj.return_to_soju.model.entity.QUser;
import com.rtsj.return_to_soju.model.entity.User;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.rtsj.return_to_soju.model.entity.QCalender.calender;
import static com.rtsj.return_to_soju.model.entity.QUser.user;

public class CalenderRepositoryImpl implements CalenderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CalenderRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<EmotionCntWithDate> getEmotionStatisticsWithPeriod(long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory.select(Projections.fields(EmotionCntWithDate.class,
                        calender.date,
                        calender.happy,
                        calender.neutral,
                        calender.angry,
                        calender.anxious,
                        calender.tired,
                        calender.sad
                )).from(calender)
                .orderBy(calender.date.asc())
                .join(user).on(user.id.eq(userId))
                .where(calender.date.between(startDate,endDate))
                .fetch();
    }

}
