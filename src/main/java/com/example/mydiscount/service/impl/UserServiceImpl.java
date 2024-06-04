package com.example.mydiscount.service.impl;

import com.example.mydiscount.dao.UserDao;
import com.example.mydiscount.entity.*;
import com.example.mydiscount.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private DiscountService discountService;
    private DiscountTypeService discountTypeService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, DiscountService discountService, DiscountTypeService discountTypeService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.discountService = discountService;
        this.discountTypeService = discountTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserById(Long userId) {
        return userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> fetchAll() {
        return userDao.findAll();
    }

    @Override
    public User upsertUser(User user) {
        User updateUser = loadUserById(user.getUserId());
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(updateUser);
    }

    @Override
    public User createUser(User user) {
        return createUser(user.getEmail(), user.getPassword(), String.valueOf(user.getRole()), user.getName());
    }

    @Override
    public User createUser(String email, String password, String role, String name) {
        User user = loadUserByEmail(email);
        if (user != null) throw new RuntimeException("User already exists");
        Role assignRole = Role.valueOf(role);
        return userDao.save(new User(email, passwordEncoder.encode(password), name, assignRole));
    }

    @Override
    public void removeUser(Long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public List<User> findUsersWithNameLike(String name) {
        return userDao.findUsersByNameContains(name);
    }

    @Override
    public boolean doesCurrentUserHasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(r -> r.getAuthority().equals(roleName));

    }
}
