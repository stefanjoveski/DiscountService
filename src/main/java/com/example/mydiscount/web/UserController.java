package com.example.mydiscount.web;

import com.example.mydiscount.entity.Role;
import com.example.mydiscount.entity.User;
import com.example.mydiscount.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.example.mydiscount.JavaCornerConstants.KEYWORD;
import static com.example.mydiscount.JavaCornerConstants.LIST_USERS;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String users(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<User> users = userService.findUsersWithNameLike(keyword);

        model.addAttribute(LIST_USERS, users);
        model.addAttribute(KEYWORD, keyword);
        return "users-views/users";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(Long userId, String keyword) {
        userService.removeUser(userId);
        return "redirect:/users/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('USER')")
    public String updateUserInfo(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "users-views/formUpdate";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String update(User user) {
        userService.upsertUser(user);
        return "redirect:/auth/logout";
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String formUser(Model model) {
        model.addAttribute("user", new User());
        return "users-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('USER')")
    public String save(@Valid User user, BindingResult bindingResult) {
        User checkUser = userService.loadUserByEmail(user.getEmail());
        if (checkUser != null) bindingResult.rejectValue("user.email", "409", "Email already exists");
        if (bindingResult.hasErrors()) return "users-views/formCreate";

        userService.createUser(user.getEmail(), user.getPassword(), String.valueOf(Role.USER), user.getName());
        return "redirect:/";
    }
}