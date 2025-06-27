package it.uniroma3.siw.siwbooks.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.siwbooks.model.Author;
import it.uniroma3.siw.siwbooks.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;



@Controller
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String showAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }
    
    @GetMapping("admin/formAuthor")
    public String formAuthor(Model model){
        model.addAttribute("author", new Author());
        return "authors/form";
    }
    @PostMapping("/admin/authors")
    public String addAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()){
            if(!authorService.alreadyExists(author)){
                authorService.save(author);
                return "redirect:/authors";
            }else{
                model.addAttribute("exists", true);
            }
        }
          
        return "authors/form";
    }
    
    @GetMapping("/authors/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model) {
        Optional<Author> authorOpt = authorService.findById(id);
        if (authorOpt.isPresent()) {
            model.addAttribute("author", authorOpt.get());
            return "/authors/details";
        } else {
            // redirect to authors list or a 404 page
            return "redirect:/authors";
        }
    }

}
