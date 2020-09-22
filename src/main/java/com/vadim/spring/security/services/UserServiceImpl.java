package com.vadim.spring.security.services;

import com.vadim.spring.security.models.Role;
import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void add(User user) {
        System.out.println("Сервис Добавить юзера - ");
        System.out.println(user.getUsername());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println(user.getAge());
        for (Role r: user.getRoles()) {
            System.out.println(r.getName());
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        User us = userRepository.getOne(id);
        userRepository.delete(us);
    }

    @Override
    @Transactional
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getById(long id) {

        return userRepository.getOne(id);
    }

    @Override
    @Transactional
    public User findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    @Transactional
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
