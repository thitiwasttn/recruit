package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.Member;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Member> findAll();

    Optional<Member> findById(Long userId);

    Member save(Member member);
}
