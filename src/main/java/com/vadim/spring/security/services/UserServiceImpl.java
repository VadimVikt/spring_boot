package com.vadim.spring.security.services;

import com.vadim.spring.security.models.User;
import com.vadim.spring.security.repositories.RoleRepository;
import com.vadim.spring.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(long id) {
        User us = userRepository.getOne(id);
        userRepository.delete(us);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(long id) {

        return userRepository.getOne(id);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
