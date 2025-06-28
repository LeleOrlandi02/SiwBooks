package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.service.AuthorService;
import it.uniroma3.siw.siwbooks.service.BookService;
import jakarta.validation.Valid;

@Controller
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.findAllSortedByDate()); // crea questo metodo dopo
        return "books/list"; // Template da creare
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        bookService.findById(id).ifPresent(book -> model.addAttribute("book", book));
        return "books/details";
    }

    @GetMapping("/books/form")
    public String showBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "books/form";
    }

    @PostMapping("/books")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/form";
        }
        bookService.save(book);
        return "redirect:/books";
    }
}
