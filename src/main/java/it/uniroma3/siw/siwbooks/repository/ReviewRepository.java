package it.uniroma3.siw.siwbooks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.model.Rewiew;

@Repository
public interface ReviewRepository extends CrudRepository<Rewiew, Long>{
    List<Rewiew> findByBookOrderByCreatedAtDesc(Book book);
}
