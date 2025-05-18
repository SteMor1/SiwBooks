package it.uniroma3.siwbooks.controller;


import it.uniroma3.siwbooks.model.Review;

import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.CredentialsService;
import it.uniroma3.siwbooks.service.ReviewService;
import it.uniroma3.siwbooks.service.UserService;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CredentialsService credentialsService; // TODO RIMUOVERE SERVE SOLO PER I TEST
    @GetMapping("/formNewReview/{book_id}")
    public String formNewReview(Model model, @PathVariable("book_id") Long id) {

        model.addAttribute("book_id", id);
        model.addAttribute("review", new Review());
        return "formNewReview";
    }
    @PostMapping("/addReviewToBook/{book_id}")
    public String addReviewToBook(@ModelAttribute("review") Review review, @PathVariable("book_id") Long book_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        review.setAuthor(credentialsService.getCredentials(userDetails.getUsername()).getUser());
        review.setBook(bookService.getBookById(book_id));
        try {
            reviewService.addReview(review);
        }catch (ValidationException e) {
            return "redirect:/book/" + book_id;
        }
        return "redirect:/book/" + book_id;
    }
}
