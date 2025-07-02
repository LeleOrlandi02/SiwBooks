package it.uniroma3.siw.siwbooks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwbooks.model.Book;
import it.uniroma3.siw.siwbooks.model.Review;
import it.uniroma3.siw.siwbooks.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Book book) {
        return reviewRepository.findByBookOrderByCreatedAtDesc(book);
    }

    public Optional<Review> getReview(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }
}
