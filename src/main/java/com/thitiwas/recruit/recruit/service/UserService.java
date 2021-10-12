package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long userId);
}
