package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findById(Long roleId);
}
