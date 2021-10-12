package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.service.MemberService;
import com.thitiwas.recruit.recruit.service.RoleService;
import com.thitiwas.recruit.recruit.service.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
