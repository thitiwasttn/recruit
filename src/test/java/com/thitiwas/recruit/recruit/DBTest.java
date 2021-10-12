package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Job;
import com.thitiwas.recruit.recruit.entity.Role;
import com.thitiwas.recruit.recruit.entity.User;
import com.thitiwas.recruit.recruit.repository.RoleRepository;
import com.thitiwas.recruit.recruit.repository.UserRepository;
import com.thitiwas.recruit.recruit.service.RoleService;
import com.thitiwas.recruit.recruit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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
        User byId = userService.findById(12L).orElseThrow();
        log.debug("byId :{}", byId);
        log.debug("jobs :{}", byId.getJobs());
    }

    @Test
    @Transactional
    public void userInsertTest() throws Exception {
        User user = User
                .builder()
                .first_name("thitiwas")
                .last_name("nupan")
                .email("test@xx.com")
                .image("noimage")
                .password("1234")
                .build();

        User save = userService.save(user);

        List<User> userAll = userService.findAll();

        log.debug("userAll :{}", userAll);

    }
}
