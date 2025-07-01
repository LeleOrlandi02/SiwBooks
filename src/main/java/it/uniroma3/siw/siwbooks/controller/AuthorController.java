package it.uniroma3.siw.siwbooks.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/*import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;*/


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
    public String saveAuthor(@ModelAttribute Author author,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException{
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "src/main/resources/static/img/author/";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, imageFile.getBytes());

            author.setImagePath("/img/author/" + fileName);
        }

        authorService.save(author);
        return "redirect:/authors";
        }

    /*@GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAuthorImage(@PathVariable Long id) {
        return authorService.findById(id)
            .filter(author -> author.getImage() != null)
            .map(author -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(author.getImage()))
            .orElse(ResponseEntity.notFound().build());
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

}
