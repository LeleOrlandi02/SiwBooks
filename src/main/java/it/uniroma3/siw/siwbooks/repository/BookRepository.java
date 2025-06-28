package it.uniroma3.siw.siwbooks.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwbooks.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAllByOrderByPublicationDateDesc(); // per ordinare i libri dal più recente
}
