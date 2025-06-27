package it.uniroma3.siw.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.service.AuthorService;
import it.uniroma3.siw.siwbooks.service.BookService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books",bookService.findAll());
        return "/books/list";
    }

    @GetMapping("/books/{id}")
    public String showBooksDetails(@PathVariable Long id, Model model) {

        Book book = bookService.findById(id).orElse(null);
        if (book == null) {
            // Handle book not found, e.g., redirect or throw exception
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        return "books/details";
    }

    @GetMapping("/admin/formBook")
    public String formNewBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "/books/form";
    }

    @PostMapping("/admin/book")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("authors", authorService.findAll());
            return "/books/form";
        }
        bookService.save(book);
        return "redirect:/books";
    }
    
    
    
}
