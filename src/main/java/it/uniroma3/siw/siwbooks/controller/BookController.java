package it.uniroma3.siw.siwbooks.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String saveBook(@Valid @ModelAttribute Book book,
                       BindingResult bindingResult,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/form";
        }

        // Gestione salvataggio immagine
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "src/main/resources/static/img/book/";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, imageFile.getBytes());

            book.setImagePath("/img/book/" + fileName);
        }

        bookService.save(book);
        return "redirect:/books";
        }

}
