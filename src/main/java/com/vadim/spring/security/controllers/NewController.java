package com.vadim.spring.security.controllers;


import com.vadim.spring.security.models.Role;
import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
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
        System.out.println(us.toString());
        return us;
    }

    @GetMapping("/klient")
    @ResponseBody
    public List<User> selectAll() {
        System.out.println("Json контроллер - получить всех пользователей");
        List<User> users = userService.allUsers();
        List<Role> roles = roleRepository.findAll();
//        for (Role r : roles) {
//            System.out.println("Роли" + r.getName());
//        }
        for (User u : users) {
            System.out.println(u.getUsername());
        }
        return users;
    }

    @GetMapping("/roles")
    @ResponseBody
    public List<Role> getRoles() {
        List<Role> roles = roleRepository.findAll();
        System.out.println("Контроллер Загрузка ролей");
//        for (Role rol : roles) {
//            System.out.println(rol.getName());
//        }
        return roles;
    }

    @PostMapping("/add_user")
    public User create(@RequestBody User user) {
        System.out.println("Новый юзер" + user.toString());
        System.out.println("Контроллер - добавить  юзера Запись в бд");
        userService.add(user);
        return user;
    }

    @PutMapping("/update_user")
    public User update(@RequestBody User user) {
        System.out.println("Прилетел юзер на обновление " + user.toString());
        userService.edit(user);
        return user;
    }

    @DeleteMapping("delete_user")
    public User delete(@RequestBody User user) {
//        User us = userService.getById(user.getId());
        System.out.println("Удаляем юзера - " + user.toString());
        userService.delete(user.getId());
        return user;
    }
}
