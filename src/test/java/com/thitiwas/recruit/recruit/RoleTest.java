package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Role;
import com.thitiwas.recruit.recruit.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = RecruitApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RoleTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void getRole() {
        List<Role> all = roleService.findAll();
        log.debug("all: {}" , all);
    }
}
