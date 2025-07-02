package it.uniroma3.siw.siwbooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Rewiew {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private Book book;
}
