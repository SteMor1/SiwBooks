package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping("/book")
    public String showBooks(Model model) {
        model.addAttribute("books",bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book",bookService.getBookById(id));
        return "book";
    }
    @GetMapping("/admin/formNewBook")
    public String formNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "admin/formNewBook";
    }
    @PostMapping("/admin/book")
    public String saveBook(@ModelAttribute("book") Book book) {
        //TODO Input Validation
        bookService.saveBook(book);
        return "redirect:/book/"+book.getId();
    }
    @GetMapping("/admin/indexBook")
    public String indexBook(Model model) {
        return "admin/indexBook";
    }


}
