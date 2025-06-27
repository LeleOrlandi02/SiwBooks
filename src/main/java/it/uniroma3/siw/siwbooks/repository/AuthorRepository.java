package it.uniroma3.siw.siwbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.siwbooks.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    boolean existsByNameAndSurname(String name, String Surname);
}
