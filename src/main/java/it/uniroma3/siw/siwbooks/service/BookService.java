package it.uniroma3.siw.siwbooks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> findAllSortedByDate() {
        return bookRepository.findAllByOrderByPublicationDateDesc();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
