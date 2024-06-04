package com.example.mydiscount.service;

import com.example.mydiscount.entity.User;

import java.util.List;

public interface UserService {
    User loadUserById(Long userId);
    User loadUserByEmail(String email);
    User findUserByName(String name);
    List<User> fetchAll();
    User upsertUser(User user);
    User createUser(User user);
    User createUser(String email, String password, String role, String name);
    void removeUser(Long userId);
    List<User> findUsersWithNameLike(String name);


    boolean doesCurrentUserHasRole(String roleName);
}
