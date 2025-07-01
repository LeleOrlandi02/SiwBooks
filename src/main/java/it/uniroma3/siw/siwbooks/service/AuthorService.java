package it.uniroma3.siw.siwbooks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwbooks.model.Author;
import it.uniroma3.siw.siwbooks.repository.AuthorRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
    }

    public List<Author> findAllSortedBySurname() {
        return authorRepository.findAllByOrderBySurnameAsc();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

   @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
}

}
