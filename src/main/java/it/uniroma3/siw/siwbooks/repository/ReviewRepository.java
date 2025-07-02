package it.uniroma3.siw.siwbooks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>{
    List<Review> findByBookOrderByCreatedAtDesc(Book book);
}
