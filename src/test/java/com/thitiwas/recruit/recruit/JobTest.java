package com.thitiwas.recruit.recruit;

import com.thitiwas.recruit.recruit.entity.Job;
import com.thitiwas.recruit.recruit.repository.JobRepository;
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
public class JobTest {

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void getJobTest() throws Exception {
        List<Job> all = jobRepository.findAll();
        log.debug("all {} ", all);
    }
}
