package com.thitiwas.recruit.recruit.repository;

import com.thitiwas.recruit.recruit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
