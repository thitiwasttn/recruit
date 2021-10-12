package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.model.RegisterM;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> findAll();

    Optional<Member> findById(Long userId);

    Member save(Member member);

    Member register(RegisterM registerM);

    Optional<Member> findByEmail(String email);
}
