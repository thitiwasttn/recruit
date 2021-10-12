package com.thitiwas.recruit.recruit.repository;

import com.thitiwas.recruit.recruit.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
