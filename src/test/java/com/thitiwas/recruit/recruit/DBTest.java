package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Job;
import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.entity.MemberProfile;
import com.thitiwas.recruit.recruit.model.MemberVideoM;
import com.thitiwas.recruit.recruit.model.RegisterM;
import com.thitiwas.recruit.recruit.model.RequestUpdateMemberFullM;
import com.thitiwas.recruit.recruit.repository.JobRepository;
import com.thitiwas.recruit.recruit.service.MemberService;
import com.thitiwas.recruit.recruit.service.RoleService;
import com.thitiwas.recruit.recruit.service.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RecruitApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class DBTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private JobRepository jobRepository;

    @Test
    @Transactional
    public void saveFullPage() throws Exception {
        Optional<Member> byId = memberService.findById(12L);
        RequestUpdateMemberFullM model = new RequestUpdateMemberFullM();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, Calendar.OCTOBER, 1);

        MemberProfile memberProfile = MemberProfile
                .builder()
                .firstName("thitiwas")
                .lastName("nupan")
                .member(byId.get())
                .birthdate(calendar.getTime())
                .address("address")
                .telno("66942518661")
                .build();
        List<Job> jobs = jobRepository.findAll();
        List<MemberVideoM> memberVideoMS = mockMemberVideo();


        model.setMemberProfile(memberProfile);
        model.setJobs(jobs);
        model.setMemberVideos(memberVideoMS);


        Member member = memberService.saveFullPage(byId.get(), model);
        log.debug("member :{}", member);
    }

    private List<MemberVideoM> mockMemberVideo() throws IOException {
        List<MemberVideoM> ret = new ArrayList<>();

        File file = new File("/Users/thitiwasnupan/work/temp/image/IMG_0333.MOV");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                file.getName(), "video/quicktime", IOUtils.toByteArray(input));

        MemberVideoM video1 = MemberVideoM
                .builder()
                .videoName(multipartFile.getOriginalFilename())
                .file(multipartFile)
                .videoModify(true)
                .build();
        ret.add(video1);
        return ret;
    }

    @Test
    public void register() throws Exception {
        RegisterM registerM = RegisterM
                .builder()
                .email("thitiwas.n@g-able.com")
                .password("Abc123")
                .confirmPassword("Abc123")
                .build();
        Member register = memberService.register(registerM);
        log.debug("register :{}", register);
    }

    @Test
    public void hashPassword() {
        String hashed = utilsService.hashStr("ABC123");
        log.debug("hashed :{}", hashed);
    }

    @Test
    @Transactional
    public void findUserById() throws Exception {
        Member byId = memberService.findById(12L).orElseThrow();
        log.debug("byId :{}", byId);
        log.debug("jobs :{}", byId.getJobs());
    }

    @Test
    @Transactional
    public void userInsertTest() throws Exception {
        Member member = Member
                .builder()
                .password("1234")
                .build();

        Member save = memberService.save(member);

        List<Member> memberAll = memberService.findAll();

        log.debug("userAll :{}", memberAll);

    }
}
