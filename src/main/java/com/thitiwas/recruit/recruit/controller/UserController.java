package com.thitiwas.recruit.recruit.controller;

import com.thitiwas.recruit.recruit.entity.User;
import com.thitiwas.recruit.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") Long userId) {
        return ResponseEntity.of(userService.findById(userId));
    }
}
