package com.thitiwas.recruit.recruit.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.thitiwas.recruit.recruit.config.Constant;
import com.thitiwas.recruit.recruit.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {
    private final MemberService memberService;

    public SecurityService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Authentication getAuthenticate() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    public DecodedJWT getDecodedJWT() {
        return (DecodedJWT) getAuthenticate().getPrincipal();
    }

    public String getType() {
        return getDecodedJWT().getClaim(Constant.JWT_USER_TYPE).asString();
    }

    public Long getId() {
        return getDecodedJWT().getClaim("id").asLong();
    }

    public Member getMember() {
        if (!getType().equals(Constant.JWT_USER_TYPE_MEMBER)) {
            throw new IllegalStateException("wrong type");
        }

        Optional<Member> member = memberService.findById(getId());

        if (member.isEmpty()) {
            throw new IllegalStateException("member not found");
        }

        return member.get();
    }
}
