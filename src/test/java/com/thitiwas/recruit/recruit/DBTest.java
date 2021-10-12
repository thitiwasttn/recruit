package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.service.RoleService;
import com.thitiwas.recruit.recruit.service.UserService;
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
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    @Transactional
    public void findUserById() throws Exception {
        Member byId = userService.findById(12L).orElseThrow();
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

        Member save = userService.save(member);

        List<Member> memberAll = userService.findAll();

        log.debug("userAll :{}", memberAll);

    }
}
