package com.thitiwas.recruit.recruit.service;

import com.thitiwas.recruit.recruit.entity.*;
import com.thitiwas.recruit.recruit.model.*;
import com.thitiwas.recruit.recruit.repository.MemberCertificationRepository;
import com.thitiwas.recruit.recruit.repository.MemberProfileRepository;
import com.thitiwas.recruit.recruit.repository.MemberRepository;
import com.thitiwas.recruit.recruit.repository.MemberVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final UtilsService utilsService;
    private final TokenService tokenService;
    private final MemberProfileRepository memberProfileRepository;
    private final FileService fileService;
    private final MemberVideoRepository memberVideoRepository;
    private final MemberCertificationRepository memberCertificationRepository;

    @Value("${custom.video.file.location}")
    private String videoLocation;
    @Value("${custom.certification.file.location}")
    private String certificationLocation;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UtilsService utilsService, TokenService tokenService, MemberProfileRepository memberProfileRepository, FileService fileService, MemberVideoRepository memberVideoRepository, MemberCertificationRepository memberCertificationRepository) {
        this.memberRepository = memberRepository;
        this.utilsService = utilsService;
        this.tokenService = tokenService;
        this.memberProfileRepository = memberProfileRepository;
        this.fileService = fileService;
        this.memberVideoRepository = memberVideoRepository;
        this.memberCertificationRepository = memberCertificationRepository;
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

    @Override
    public Member getMemberProcess(Member member) {
        return member;
    }

    @Override
    public Member saveFullPage(Member member, RequestUpdateMemberFullM model) throws IOException {
        if (model.getMemberProfile().getMember().getId().longValue() != member.getId().longValue()) {
            throw new IllegalStateException();
        }

        saveMemberVideoApi(member, model.getMemberVideos());


        return findById(member.getId()).orElseThrow();
    }

    @Override
    public MemberVideo saveVideo(Member member, MultipartFile video) throws IOException {
        MemberVideo entity = new MemberVideo();

        entity.setMember(member);
        entity.setVideoName(video.getName());
        MemberVideo save = memberVideoRepository.save(entity);

        File videoPath = new File(videoLocation.concat("/").concat(String.valueOf(member.getId())));
        boolean exists = videoPath.exists();
        if (!exists) {
            boolean mkdirs = videoPath.mkdirs();
            if (!mkdirs) {
                throw new IOException();
            }
        }
        String videoNameWithExtension = String.valueOf(save.getId()).concat(".").concat(fileService.getFileExtension(video));
        fileService.write(videoPath.getAbsolutePath(),
                videoNameWithExtension,
                video);

        save.setVideoName(videoNameWithExtension);

        return memberVideoRepository.save(entity);
    }

    private void saveMemberVideoApi(Member member, List<MemberVideoM> memberVideos) throws IOException {
        for (MemberVideoM memberVideo : memberVideos) {
            if (memberVideo.isVideoModify()) {

                saveVideo(member, memberVideo.getFile());

            }
        }
    }

    @Override
    public void deleteVideo(Long videoId) throws IOException {
        MemberVideo memberVideo = memberVideoRepository.findById(videoId)
                .orElseThrow();

        String pathFile = videoLocation
                .concat("/")
                .concat(String.valueOf(memberVideo.getMember().getId()))
                .concat("/")
                .concat(memberVideo.getVideoName());

        File file = new File(pathFile);
        boolean delete = file.delete();
        memberVideoRepository.delete(memberVideo);

    }

    @Override
    public MemberCertificate saveCert(Member member, RequestMemberCertificationM model) throws IOException {
        MultipartFile file = model.getCertification();
        MemberCertificate entity = new MemberCertificate();
        entity.setMember(member);
        entity.setCertificateName(file.getOriginalFilename());
        entity.setUpdateDate(new Date());
        entity = memberCertificationRepository.save(entity);

        File certificationPath = new File(certificationLocation.concat("/").concat(String.valueOf(member.getId())));
        boolean exists = certificationPath.exists();
        if (!exists) {
            boolean mkdirs = certificationPath.mkdirs();
            if (!mkdirs) {
                throw new IOException();
            }
        }
        String certNameWithExtension = String.valueOf(entity.getId()).concat(".").concat(fileService.getFileExtension(file));
        fileService.write(certificationPath.getAbsolutePath(),
                certNameWithExtension,
                file);

        entity.setCertificateName(certNameWithExtension);

        return memberCertificationRepository.save(entity);
    }

    @Override
    public void deleteCert(Long certId) {
        MemberCertificate memberCertificate = memberCertificationRepository.findById(certId)
                .orElseThrow();

        String pathFile = certificationLocation
                .concat("/")
                .concat(String.valueOf(memberCertificate.getMember().getId()))
                .concat("/")
                .concat(memberCertificate.getCertificateName());

        File file = new File(pathFile);
        boolean delete = file.delete();
        memberCertificationRepository.delete(memberCertificate);
    }

    @Override
    public Member deleteAndUpdateJob(Member member, List<Job> jobs) {
        member.setJobs(jobs);
        return save(member);
    }
}
