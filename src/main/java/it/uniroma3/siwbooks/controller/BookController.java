package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping("/book")
    public String showBooks(Model model) {
        model.addAttribute("books",bookService.getAllBooks());
        return "books";
    }
}
