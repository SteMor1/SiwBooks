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
    public Review addReview(Review review) throws ValidationException {
        if(reviewRepository.existsByBookIdAndAuthorId(review.getBook().getId(),review.getAuthor().getId())){
            throw new ValidationException("Review already exists");
        }
        return reviewRepository.save(review);
    }
    public boolean existsByBookAndAuthor(Long bookId, Long userId) {
        return reviewRepository.existsByBookIdAndAuthorId(bookId,userId);
    };
    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
    public void save(Review review) {
        reviewRepository.save(review);
    }
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

}
