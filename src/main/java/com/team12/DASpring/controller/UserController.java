package com.team12.DASpring.controller;


import com.team12.DASpring.entity.User;
import com.team12.DASpring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private View error;

    @GetMapping("/login")
    public String login() {return "user/login";}

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error->model.addAttribute(error.getField()+"_error", error.getDefaultMessage()));
            return "user/register";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

}
