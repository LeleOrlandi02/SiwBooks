package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siwbooks.model.User;
import it.uniroma3.siw.siwbooks.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/users/{id}")
    public String usersDetails(@PathVariable Long id, Model model) {
        userService.findById(id).ifPresent(u -> model.addAttribute("user", u));
        return "users/details";
    }

    @GetMapping("/admin/users")
    public String formUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        
        if(bindingResult.hasErrors()){
            return "users/form";
        }
        userService.save(user);
        return "redirect:/users";
    }
    
}
