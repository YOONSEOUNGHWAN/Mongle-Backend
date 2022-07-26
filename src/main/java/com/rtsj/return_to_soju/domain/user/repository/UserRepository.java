package com.rtsj.return_to_soju.domain.user.repository;

import com.rtsj.return_to_soju.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
