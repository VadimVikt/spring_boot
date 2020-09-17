package com.vadim.spring.security.controllers;

import com.vadim.spring.security.models.User;
import com.vadim.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {


    @Autowired
    UserService userService;

    @GetMapping("/")
    public String homePage() {

        return "home";
    }

    @GetMapping(value = "/all")
    public String getAllUsers(Model model) {
        String title = "Все пользователи";
        List<User> users = userService.allUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }
        model.addAttribute("title", title);
        model.addAttribute("listUsers", users);
        return "users";
    }
    @GetMapping(value = "/user")
    public String getUser(Model model, Principal principal) {
        System.out.println("Получаем юзера");
        User us = userService.findByUsername(principal.getName());
        System.out.println(us.toString());
        String title = "Добрый день";
        model.addAttribute("user", us);

        return "user";
    }
    @GetMapping(value = "edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        System.out.println("Редактировать ");
        User us = userService.getById(id);
        String title = "Привет";
        model.addAttribute("title", title);
        model.addAttribute("user", us);
        System.out.println("Нашли - " + us.toString());
        userService.edit(us);
        return "edituser";
    }

    @PostMapping(value = "/edituser")
    public String updateUser(@ModelAttribute User user) {
        System.out.println("Пришли на форму редактирования");
        System.out.println("новый юзер - " + user.toString());
        userService.edit(user);
        return "redirect:/all";
    }

    @GetMapping("/remove/{id}")
    public String removeUser(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/all";
    }
}
