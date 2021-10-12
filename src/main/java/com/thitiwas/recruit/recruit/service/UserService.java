package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long userId);

    User save(User user);
}
