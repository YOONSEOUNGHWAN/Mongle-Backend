package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.DailySentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySentenceRepository extends JpaRepository<DailySentence, Long> {
}
