package com.example.mydiscount.service.impl;

import com.example.mydiscount.dto.UserLoginDto;
import com.example.mydiscount.dto.UserRegisterDto;
import com.example.mydiscount.entity.Role;
import com.example.mydiscount.entity.User;
import com.example.mydiscount.service.AuthService;
import com.example.mydiscount.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String validateAndLogin(UserLoginDto userLoginDto) {
        User user = userService.loadUserByEmail(userLoginDto.getEmail());
        if (user == null) {
            return "The email is not registered";
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return "The email and password does not match";
        }
        return "Success";
    }

    @Override
    public boolean validateEmail(String email) {
        if ((email == null) || (email.length() == 0)) {
            return false;
        }
        String regex = "^[A-Za-z0-9_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean validatePassword(String password) {
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{7,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public String validateAndRegister(UserRegisterDto userRegisterDto) {
        if (!validateEmail(userRegisterDto.getEmail())) {
            return "The email is in invalid format";
        }
        User u = userService.loadUserByEmail(userRegisterDto.getEmail());
        if (u != null) {
            return "The email is already in use";
        }
        if (!validatePassword(userRegisterDto.getPassword())) {
            return "The password is invalid";
        }

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setName(userRegisterDto.getName());
        user.setRole(Role.USER);
        user.setPassword(userRegisterDto.getPassword());
        userService.createUser(user);

        return "Success";
    }
}
