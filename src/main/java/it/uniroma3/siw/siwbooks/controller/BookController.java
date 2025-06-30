package it.uniroma3.siw.siwbooks.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model,
                           @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/form";
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            book.setImage(imageFile.getBytes());
        }
        
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("books/{id}/image")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {
        return bookService.findById(id)
            .filter(book -> book.getImage() != null)
            .map(book -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(book.getImage()))
            .orElse(ResponseEntity.notFound().build());
    }
}
