package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Member> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Member save(Member member) {
        return userRepository.save(member);
    }
}
