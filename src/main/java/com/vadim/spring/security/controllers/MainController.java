package com.vadim.spring.security.controllers;

import com.vadim.spring.security.models.User;
import com.vadim.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String homePage() {

        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "secured part of web service " + "<p>" + "<b>" + user.getUsername() + "</b>" + " " + user.getEmail() +"</p>";
    }

    @GetMapping("/read_profile")
    public String pageForReadProfile() {

        return "read profile page ";
    }

    @GetMapping("/only_for_admins")
    public String pageOnlyForAdmins() {

        return "admins page ";
    }
}
