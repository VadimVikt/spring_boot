package com.vadim.spring.security.controllers;


import com.vadim.spring.security.models.Role;
import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
public class NewController {

    final UserService userService;

    final RoleRepository roleRepository;

    public NewController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/findOne/{id}")
    @ResponseBody
    public User findOne(@PathVariable Long id) {

        System.out.println(id);
        User us = userService.getById(id);
        User uuu = userService.findByUsername(us.getUsername());
        User uss = new User();
        System.out.println(us.toString());
        System.out.println(uuu.toString());
//        Set <Role> r =  us.getRoles();
//        uss.setRoles((Set<Role>) r);
        return uuu;
    }
}
