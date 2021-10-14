package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.entity.MemberProfile;
import com.thitiwas.recruit.recruit.model.LoginM;
import com.thitiwas.recruit.recruit.model.RegisterM;
import com.thitiwas.recruit.recruit.model.ResponseLoginM;
import com.thitiwas.recruit.recruit.repository.MemberProfileRepository;
import com.thitiwas.recruit.recruit.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final UtilsService utilsService;
    private final TokenService tokenService;
    private final MemberProfileRepository memberProfileRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UtilsService utilsService, TokenService tokenService, MemberProfileRepository memberProfileRepository) {
        this.memberRepository = memberRepository;
        this.utilsService = utilsService;
        this.tokenService = tokenService;
        this.memberProfileRepository = memberProfileRepository;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long userId) {
        return memberRepository.findById(userId);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member register(RegisterM register) {
        // check same password
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            throw new IllegalStateException("password don't match");
        }

        // check email exist
        if (memberRepository.findByEmail(register.getEmail()).isPresent()) {
            throw new IllegalStateException("email have already used");
        }

        // insert
        Member afterSave = memberRepository.save(Member
                .builder()
                .email(register.getEmail())
                .password(utilsService.hashStr(register.getPassword()))
                .status(true)
                .build());

        log.info("afterSave :{}", afterSave);

        return afterSave;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public ResponseLoginM login(LoginM loginM) throws IllegalAccessException {
        Optional<Member> member = memberRepository.findByEmailAndPassword(loginM.getEmail(), loginM.getPassword());
        if (member.isEmpty()) {
            throw new IllegalAccessException("Member not found");
        }

        String token = tokenService.createToken(member.get());

        return ResponseLoginM
                .builder()
                .token(token)
                .build();
    }

    @Override
    public MemberProfile addProfile(Member member, MemberProfile memberProfile) {
        // TODO valid
        memberProfile.setMember(member);

        log.debug("memberProfile :{}", memberProfile);

        return memberProfileRepository.saveAndFlush(memberProfile);
    }

    @Override
    public MemberProfile updateProfile(MemberProfile memberProfile) {
        return memberProfileRepository.save(memberProfile);
    }

    @Override
    public MemberProfile updateProfileProcess(Member member, MemberProfile memberProfile) {
        Optional<MemberProfile> refreshProfile = memberProfileRepository.findById(memberProfile.getId());
        if (refreshProfile.isEmpty()) {
            throw new IllegalStateException("profile not found");
        }
        // log.debug("memberProfile.getMember() :{}", refreshProfile.get().getMember());
        if (refreshProfile.get().getMember().getId().longValue() != member.getId().longValue()) {
            throw new IllegalStateException("member id not match");
        }

        memberProfile.setMember(member);

        return memberProfileRepository.saveAndFlush(memberProfile);
    }
}
