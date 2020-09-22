package com.vadim.spring.security.controllers;

import com.vadim.spring.security.models.Role;
import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    final UserService userService;

    final RoleRepository roleRepository;


    @Autowired
    public MainController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String homePage() {

        return "home";
    }
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/all")
    public String getAllUsers(Model model, Principal principal) {
        String title = "Admin panel";

        User loggedUser = userService.findByUsername(principal.getName());
        List<User> users = userService.allUsers();
        for (User u : users) {
            Collection<Role> roles = u.getRoles();
            System.out.print(u.getUsername() + " " + u.getEmail() + " Роль - " );
            System.out.println(u.getRoles().size());
            for (Role r : roles) {
                System.out.println(r.getName());
            }
        }
        List<Role> roles =  roleRepository.findAll();

        for (Role r : roles) {
            System.out.println("Все роли - " + r.getName());
        }
        boolean isAdmin = false;
        for (Role r : loggedUser.getRoles()) {
            if (r.getName().equals("ADMIN")) {
                System.out.println("isAdmin - true");
                isAdmin = true;
            }
        }
        model.addAttribute("title", title);
        model.addAttribute("listUsers", users);
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("allRoles", roles);

        return "users";
    }
    @GetMapping(value = "/user")
    public String getUser(Model model, Principal principal) {
        String title = "User information page";
        System.out.println("Получаем юзера");
        User us = userService.findByUsername(principal.getName());
        System.out.println("Имя пользователя " + principal.getName());
        System.out.println(us.toString());
        model.addAttribute("title", title);
        model.addAttribute("user", us);
        boolean isAdmin = false;
        for (Role r : us.getRoles()) {
            if (r.getName().equals("ADMIN")) {
                System.out.println("isAdmin - true");
                isAdmin = true;
            }
        }
        model.addAttribute("isAdmin", isAdmin);

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

    @PostMapping(value = "/adduser", produces = "text/html; charset=utf-8")
    public String addUser(User user) {
        System.out.println("Контроллер Добавить - " + user.toString());
        userService.add(user);
        return "redirect:/all";
    }

    @PostMapping(value = "/user_create", produces = "text/html; charset=utf-8")
    public String createUser(User user) {
        System.out.println("Контроллер Добавить -- " + user.toString());
        System.out.println("Роли " + user.getRoles());

        userService.add(user);
        return "redirect:/all";
    }
    @PostMapping("/add")
    public ModelAndView addNewUser(@ModelAttribute("user") User user, String[] myroles ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/all");
        System.out.println(user.toString());
//        System.out.println(username + " " + lastname + " " + email + " " + age + " " + password + " Роли - " );
        for (String  a : myroles) {
            System.out.println("Роли - " + a);
        }
//        System.out.println("Новый контроллер -  " + user.toString());
        System.out.println("======================================");
//        userService.add(user);
        return modelAndView;
    }
    @PostMapping(value = "/user_update")
    public String updateUserNew(@ModelAttribute("user") User user, String[] myRoles) {
        System.out.println("Обновляем юзера");
        System.out.println(user.toString());
        Role rol = new Role();
        Set<Role> r = new HashSet<>();
        for (String  a : myRoles) {
            rol.setName(a);
            r.add(rol);
            System.out.println("Роли - " + a);
        }
        user.setRoles(r);
        System.out.println("===========================================");

        userService.add(user);
        return "redirect:/all";
    }

//    @GetMapping("/findOne/{id}")
//    @ResponseBody
//    public User findOne(@PathVariable  Long id) {
//
//        System.out.println(id);
//        User us = userService.getById(id);
//        User uuu = userService.findByUsername("admin");
//        User uss = new User();
//        System.out.println(us.toString());
//        System.out.println(uuu.toString());
//        return uss;
//    }
}
