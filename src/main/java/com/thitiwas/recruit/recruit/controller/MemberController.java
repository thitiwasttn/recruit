package com.thitiwas.recruit.recruit.controller;

import com.thitiwas.recruit.recruit.entity.Member;
import com.thitiwas.recruit.recruit.model.LoginM;
import com.thitiwas.recruit.recruit.model.RegisterM;
import com.thitiwas.recruit.recruit.model.ResponseLoginM;
import com.thitiwas.recruit.recruit.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<Member> findUserById(@PathVariable("userId") Long userId) {
        log.debug("findUserById()");
        return ResponseEntity.of(memberService.findById(userId));
    }

    @PutMapping("/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody RegisterM register) {
        memberService.register(register);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<ResponseLoginM> register(@RequestBody LoginM loginM) throws IllegalAccessException {
        return ResponseEntity.ok(memberService.login(loginM));
    }
}
