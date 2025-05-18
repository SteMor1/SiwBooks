package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.repository.ReviewRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
