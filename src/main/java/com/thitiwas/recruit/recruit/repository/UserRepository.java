package com.thitiwas.recruit.recruit.repository;

import com.thitiwas.recruit.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
