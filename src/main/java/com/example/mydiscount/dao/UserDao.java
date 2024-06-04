package com.example.mydiscount.dao;

import com.example.mydiscount.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findUsersByNameContains(String name);
    User findByEmail(String email);
    User findByName(String name);

}
