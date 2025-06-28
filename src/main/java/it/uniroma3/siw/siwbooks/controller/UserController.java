package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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