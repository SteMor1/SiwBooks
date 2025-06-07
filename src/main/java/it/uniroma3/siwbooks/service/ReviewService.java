package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.repository.ReviewRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public boolean existsByBookAndAuthor(Long bookId, Long userId) {
        return reviewRepository.existsByBookIdAndAuthorId(bookId,userId);
    };

    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
    public List<Review> getBookReviews(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

}
