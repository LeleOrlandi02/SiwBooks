package it.uniroma3.siw.siwbooks.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwbooks.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAllByOrderBySurnameAsc(); // Optional: for listing authors alphabetically
}
