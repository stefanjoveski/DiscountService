package com.example.mydiscount.service;

import com.example.mydiscount.dto.UserLoginDto;
import com.example.mydiscount.dto.UserRegisterDto;

public interface AuthService {
    String validateAndLogin(UserLoginDto userLoginDTO);
    String validateAndRegister(UserRegisterDto userRegisterDto);
    boolean validateEmail(String email);
    boolean validatePassword(String password);

}
