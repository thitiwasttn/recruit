package com.thitiwas.recruit.recruit;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RecruitApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class DBTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    public void findUserById() throws Exception {
        User byId = userService.findById(12L).orElseThrow();
        /*Hibernate.initialize(byId.getJobs());
        Hibernate.initialize(byId.getRole());
        Hibernate.initialize(byId.getUserVideos());*/
        log.debug("byId :{}", byId);
    }

    @Test
    @Transactional
    public void userInsertTest() throws Exception {
        Role roleAdmin = roleService.findById(2L).orElseThrow();
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        User user = User
                .builder()
                .first_name("thitiwas")
                .last_name("nupan")
                .email("test@xx.com")
                .image("noimage")
                .password("1234")
                .role(roles)
                .build();

        User save = userService.save(user);

        List<User> userAll = userService.findAll();

        log.debug("userAll :{}", userAll);

    }
}
