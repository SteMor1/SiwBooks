package it.uniroma3.siwbooks.controller.validator;

import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.service.ReviewService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator implements Validator {
    @Autowired
    private ReviewService reviewService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Review.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Review review = (Review) target;
        if(review.getAuthor() != null &&review.getBook() != null && reviewService.existsByBookAndAuthor(review.getBook().getId(),review.getAuthor().getId())) {
            errors.reject("review.duplicate");
        }

    }



}
