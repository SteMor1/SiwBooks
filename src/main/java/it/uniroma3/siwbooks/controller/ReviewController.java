package it.uniroma3.siwbooks.controller;


import it.uniroma3.siwbooks.controller.validator.ReviewValidator;
import it.uniroma3.siwbooks.model.Review;

import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.CredentialsService;
import it.uniroma3.siwbooks.service.ReviewService;

import it.uniroma3.siwbooks.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewValidator reviewValidator;

    @GetMapping("/formNewReview/{book_id}")
    public String formNewReview(Model model, @PathVariable("book_id") Long id) {
        Review review = new Review();
        review.setBook(bookService.getBookById(id));
        review.setAuthor(userService.getCurrentUser());
        model.addAttribute("book_id", id);
        model.addAttribute("review",review );
        return "formNewReview";
    }
    @PostMapping("/addReviewToBook/{book_id}")
    public String addReviewToBook(@Valid @ModelAttribute("review") Review review, BindingResult reviewBindingResult, @PathVariable("book_id") Long book_id) {

        review.setAuthor(userService.getCurrentUser()); //Evito che un utente possa provare a modificare l'id utente tramite il form
        this.reviewValidator.validate(review, reviewBindingResult);
        if(reviewBindingResult.hasErrors()) {
            boolean duplicate = reviewBindingResult.getGlobalErrors().stream().anyMatch(e -> "review.duplicate".equals(e.getCode()));
            if (duplicate) {
                return "redirect:/book/" + book_id;
            }
            return "formNewReview";
        }
        reviewService.save(review);
        return "redirect:/book/" + book_id;
    }
    @GetMapping("/updateReview/{id}")
    public String updateReview(@PathVariable("id") Long id, Model model) {
        Review review = reviewService.getReview(id);
        if(review.getAuthor()!=userService.getCurrentUser()){
            return "redirect:/book/" + bookService.getBookById(review.getBook().getId()).getId();
        }
        model.addAttribute("review", review);
        return  "formUpdateReview";
    }
    @PostMapping("/updateReview")
    public String updateReview(@ModelAttribute("review") Review review) {
        System.out.println(review.getId());
        Review originalReview = reviewService.getReview(review.getId());
        if(userService.getCurrentUser() == null || ! originalReview.getAuthor().getId().equals(userService.getCurrentUser().getId())){
            return "redirect:/book/" + originalReview.getBook().getId();
        }
        originalReview.setTitle(review.getTitle());
        originalReview.setText(review.getText());
        originalReview.setRating(review.getRating());
        reviewService.save(originalReview);
        return "redirect:/book/" + originalReview.getBook().getId();
    }
    @GetMapping("/deleteReview/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        Review review = reviewService.getReview(id);
        Long bookId = review.getBook().getId();
        if(userService.getCurrentUser() == null || ! review.getAuthor().getId().equals(userService.getCurrentUser().getId())){
            return "redirect:/book/" + bookId;
        }
        reviewService.delete(review.getId());
        return "redirect:/book/" + bookId;
    }
}
