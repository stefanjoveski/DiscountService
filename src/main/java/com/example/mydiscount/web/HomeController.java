package com.example.mydiscount.web;


import com.example.mydiscount.dto.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", new UserLoginDto());

        return "users-views/login";
    }
}
