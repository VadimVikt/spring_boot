package com.vadim.spring.security.controllers;

import com.vadim.spring.security.models.Role;
import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/new")
    public String newLink(Model model, Principal principal) {
        String title = "Admin panel";
        User loggedUser = userService.findByUsername(principal.getName());
        List<Role> roles =  roleRepository.findAll();
        boolean isAdmin = false;
        for (Role r : loggedUser.getRoles()) {
            if (r.getName().equals("ADMIN")) {
                System.out.println("isAdmin - true");
                isAdmin = true;
            }
        }
        model.addAttribute("title", title);
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("allRoles", roles);
        return "new";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        System.out.println("Старт приложения");
        String title = "Admin panel";
        User loggedUser = userService.findByUsername(principal.getName());
        List<Role> roles =  roleRepository.findAll();
        boolean isAdmin = false;
        for (Role r : loggedUser.getRoles()) {
            if (r.getName().equals("ADMIN")) {
                System.out.println("isAdmin - true");
                isAdmin = true;
            }
        }
        model.addAttribute("title", title);
        model.addAttribute("user", loggedUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("allRoles", roles);
        return "admin";
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


    @PostMapping(value = "/user_create")
    public String updateUserNew(@ModelAttribute("user") User user, String[] myRoles) {
        System.out.println("Обновляем юзера");
        System.out.println(user.toString());
        Role rol = new Role();
        Set<Role> r = new HashSet<>();
        for (String  a : myRoles) {
            rol = roleRepository.findByName(a);
            r.add(rol);
            System.out.println("Роли - " + a);
        }
        user.setRoles(r);
        System.out.println("===========================================");

        userService.add(user);
        return "redirect:/all";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        System.out.println("Получили юзера для удаления - " + user.getUsername());
        System.out.println("Удаляем по ИД - " + user.getId());
        userService.delete(user.getId());
        return "redirect:/all";
    }


}
