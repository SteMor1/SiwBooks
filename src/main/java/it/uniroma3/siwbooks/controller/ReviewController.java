package it.uniroma3.siwbooks.controller;


import it.uniroma3.siwbooks.model.Review;

import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.CredentialsService;
import it.uniroma3.siwbooks.service.ReviewService;

import it.uniroma3.siwbooks.service.UserService;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("/formNewReview/{book_id}")
    public String formNewReview(Model model, @PathVariable("book_id") Long id) {

        model.addAttribute("book_id", id);
        model.addAttribute("review", new Review());
        return "formNewReview";
    }
    @PostMapping("/addReviewToBook/{book_id}")
    public String addReviewToBook(@ModelAttribute("review") Review review, @PathVariable("book_id") Long book_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        review.setAuthor(userService.getCurrentUser());
        review.setBook(bookService.getBookById(book_id));
        try {
            reviewService.addReview(review);
        }catch (ValidationException e) {
            return "redirect:/book/" + book_id;
        }
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
