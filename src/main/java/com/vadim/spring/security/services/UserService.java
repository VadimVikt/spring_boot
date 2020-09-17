package com.vadim.spring.security.services;

import com.vadim.spring.security.models.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    void add(User user);
    void delete(long id);
    void edit(User user);
    User getById(long id);
    User findByUsername(String name);
}
