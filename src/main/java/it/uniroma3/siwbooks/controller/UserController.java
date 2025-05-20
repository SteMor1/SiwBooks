package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        model.addAttribute("user",userService.getCurrentUser());
        return "profile";
    }
}
