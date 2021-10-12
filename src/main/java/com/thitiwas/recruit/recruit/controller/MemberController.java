package com.thitiwas.recruit.recruit.controller;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class MemberController {

    private final UserService userService;

    @Autowired
    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<Member> findUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.of(userService.findById(userId));
    }
}
