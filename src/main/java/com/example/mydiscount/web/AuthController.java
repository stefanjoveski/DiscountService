package com.example.mydiscount.web;

import com.example.mydiscount.dto.UserLoginDto;
import com.example.mydiscount.dto.UserRegisterDto;
import com.example.mydiscount.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.mydiscount.entity.Role.USER;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return "users-views/login";
    }

    @PostMapping("/login-success")
    public String loginSuccess(@Valid UserLoginDto userLoginDto, Model model, Principal principal) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        } catch (Exception e) {
            model.addAttribute("user", userLoginDto);
            model.addAttribute("error", e.getMessage());
            return "users-views/login";
        }
        String err = authService.validateAndLogin(userLoginDto);
        if (err.equals("Success")) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(String.valueOf(USER));
            if (authentication.getAuthorities().contains(grantedAuthority)){
                return "redirect:/discounts/index/user";
            }else{
                return "redirect:/discounts/index";
            }
        }
        model.addAttribute("error", err);

        return "users-views/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "users-views/register";
    }

    @PostMapping("/register-success")
    public String registerSuccess(@Valid UserRegisterDto userRegisterDto, Model model) {
        String err = authService.validateAndRegister(userRegisterDto);
        if (!err.equals("Success")) {
            model.addAttribute("user", userRegisterDto);
            return "users-views/register";
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRegisterDto.getEmail(), userRegisterDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/home").getUrl();
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        SecurityContextHolder.getContext().setAuthentication(null);
        model.addAttribute("user", new UserLoginDto());
        return "users-views/login";
    }
}
