package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.*;
import com.thitiwas.recruit.recruit.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> findAll();

    Optional<Member> findById(Long userId);

    Member save(Member member);

    Member register(RegisterM registerM);

    Optional<Member> findByEmail(String email);

    ResponseLoginM login(LoginM loginM) throws IllegalAccessException;

    MemberProfile addProfile(Member member, MemberProfile memberProfile);

    MemberProfile updateProfile(MemberProfile memberProfile);

    MemberProfile updateProfileProcess(Member member, MemberProfile memberProfile);

    Member getMemberProcess(Member member);

    Member saveFullPage(Member member, RequestUpdateMemberFullM model) throws IOException;

    MemberVideo saveVideo(Member member, MultipartFile video) throws IOException;

    void deleteVideo(Long videoId) throws IOException;

    MemberCertificate saveCert(Member member, RequestMemberCertificationM model) throws IOException;

    void deleteCert(Long certId);

    Member deleteAndUpdateJob(Member member, List<Job> jobs);

    void memberAddImage(Member member, MultipartFile file) throws IOException;
}
