package com.example.demospringsecurity.controller;

import com.example.demospringsecurity.auth.SimpleLoginUser;
import com.example.demospringsecurity.entity.User;
import com.example.demospringsecurity.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping(value = "members")
public class ContentsController {
    private final ContentService contentService;

    public ContentsController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping(value = "/")
    public String any(Principal principal){
        Authentication authentication = (Authentication) principal;
        SimpleLoginUser loginUser = (SimpleLoginUser) authentication.getPrincipal();
        log.info("id:{}, name:{}", loginUser.getUser().getId(), loginUser.getUser().getName());
        contentService.doService();
        return "members/index";
    }

    @GetMapping(value = "user")
    public String user(@AuthenticationPrincipal SimpleLoginUser loginUser) {
        log.info("id:{}, name:{}", loginUser.getUser().getId(), loginUser.getUser().getName());
        return "members/user/index";
    }

    @GetMapping(value = "admin")
    public String admin(@AuthenticationPrincipal(expression = "user") User user) {
        log.info("id:{}, name:{}", user.getId(), user.getName());
        return "members/admin/index";
    }
}
