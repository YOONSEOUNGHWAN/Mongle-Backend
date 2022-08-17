package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    Optional<Calender> findByUserAndDate(User user, LocalDate date);
    List<Calender> findALLByUserAndDateBetween(User user, LocalDate start, LocalDate end);

}
