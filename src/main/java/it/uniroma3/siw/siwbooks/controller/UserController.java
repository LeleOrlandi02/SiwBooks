package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siwbooks.model.User;
import it.uniroma3.siw.siwbooks.service.UserService;
import jakarta.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // No need for PasswordEncoder here as UserService handles it for registration

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

    @GetMapping("/admin/users/form")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @PostMapping("/admin/users")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "users/form";
        }
        userService.save(user); // Password assumed to be encoded by a specific method or handled elsewhere
        return "redirect:/users";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               @RequestParam(value = "adminSecret", required = false) String adminSecret,
                               Model model) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.user", "Email già in uso");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if ("segretoadmin".equals(adminSecret)) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        userService.registerNewUser(user); // Use the new dedicated registration method

        return "redirect:/login";
    }

    // --- THE MISSING PART: Controller for the login page ---
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This points to src/main/resources/templates/login.html
    }
    // --------------------------------------------------------
}