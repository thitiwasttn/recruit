package com.thitiwas.recruit.recruit.controller;

import com.thitiwas.recruit.recruit.config.Constant;
import com.thitiwas.recruit.recruit.entity.Job;
import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.service.JobService;
import com.thitiwas.recruit.recruit.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/jobs")
@Slf4j
public class JobController {
    private final JobService jobService;
    private final SecurityService securityService;

    public JobController(JobService jobService, SecurityService securityService) {
        this.jobService = jobService;
        this.securityService = securityService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }
}
