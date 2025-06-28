package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.siwbooks.model.Author;
import it.uniroma3.siw.siwbooks.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/{id}")
    public String authorDetails(@PathVariable Long id, Model model) {
        authorService.findById(id).ifPresent(author -> model.addAttribute("author", author));
        return "authors/details";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/form";
    }

    @PostMapping
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }
}
