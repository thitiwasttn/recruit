package com.thitiwas.recruit.recruit.repository;

import com.thitiwas.recruit.recruit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
}
