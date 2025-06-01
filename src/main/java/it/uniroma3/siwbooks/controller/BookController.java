package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.controller.validator.BookValidator;
import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.model.Image;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookValidator bookValidator;

    @GetMapping("/book")
    public String showBooks(@RequestParam(required = false) String title,@RequestParam(required = false) String author, @RequestParam(required = false) Integer yearFrom, @RequestParam(required = false) Integer yearTo, Model model) {
        if (yearFrom != null || yearTo != null) {
            System.out.println("yearFrom: " + yearFrom+" yearTo: " + yearTo);
           model.addAttribute("books", bookService.findByYear(yearFrom != null ? yearFrom : 0, yearTo != null ? yearTo : Year.now().getValue())); //Ricerco per anno : se yearFrom è null uso 0 se yearTo è null uso l'anno corrente
        }else if(author != null) {
            model.addAttribute("books", bookService.findByAuthor(author));
        }
        else if(title != null) {
            model.addAttribute("books", bookService.findByTitleStartingWith(title));
        }else{
            model.addAttribute("books",bookService.getAllBooks());
        }
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
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bookBindingResult, @RequestParam("bookCover") MultipartFile bookCoverUpload, @RequestParam("otherImages") MultipartFile[] bookImagesUpload) {

        bookValidator.validate(book, bookBindingResult);
        if(bookBindingResult.hasErrors()) {
            return "admin/formNewBook";
        }
        List<Image> bookImages = new ArrayList<Image>();
        Image bookCover = new Image();
        try {
            bookCover.setData(bookCoverUpload.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (MultipartFile file : bookImagesUpload) {
            if (!file.isEmpty()) {
                Image img = new Image();
                try {
                img.setData(file.getBytes());
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
                bookImages.add(img);
            }
        }

        book.setBookImages(bookImages);
        book.setCoverImage(bookCover);
        //TODO Input Validation
        bookService.saveBook(book);
        return "redirect:/book/"+book.getId();
    }
    @GetMapping("/admin/indexBook")
    public String indexBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/indexBook";
    }
    @GetMapping("/admin/formUpdateBook/{id}")
    public String updateAuthor(@PathVariable("id") Long id,Model model) {
        model.addAttribute("book", bookService.getBookById(id)); //TODO AGGIUNGERE LA POSSIBILITÀ DI MODIFICARE LE IMMAGINI
        return "admin/formUpdateBook";
    }
    @PostMapping("/admin/updateBook")
    public String updateAuthor(@ModelAttribute("book") Book book, Model model) {
        bookService.updateBook(book);
        return "redirect:/admin/indexBook";
    }

    @GetMapping("/admin/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id,Model model) {

        bookService.deleteBook(id);
        return "redirect:/admin/indexBook";
    }
    @GetMapping("/admin/updateAuthors/{id}")
    public String updateAuthors(@PathVariable("id") Long id,Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        Iterable<Author> availableAuthors = authorService.findAuthorsNotInBook(id);
        model.addAttribute("availableAuthors", availableAuthors);
        return "admin/formUpdateAuthors";
    }
    @GetMapping("/admin/addAuthorToBook/{authorId}/{bookId}")
    public String aggiungiAttore(@PathVariable("bookId") Long bookId,@PathVariable("authorId") Long authorId,Model model) {

        this.bookService.addAuthorToBook(authorId,bookId);
        Book book = this.bookService.getBookById(bookId);
        Iterable<Author> availableAuthors = authorService.findAuthorsNotInBook(bookId);
        model.addAttribute("availableAuthors", availableAuthors);
        return "redirect:/admin/updateAuthors/"+bookId;
    }

    @GetMapping("/admin/removeAuthorFromBook/{authorId}/{bookId}")
    public String removeActor(@PathVariable("authorId") Long authorId,@PathVariable("bookId") Long bookId,Model model) {

        this.bookService.removeAuthorFromBook(authorId,bookId);
        Book book = this.bookService.getBookById(bookId);
        model.addAttribute("book",book);
        Iterable<Author> availableAuthors = authorService.findAuthorsNotInBook(bookId);
        model.addAttribute("availableAuthors", availableAuthors);
        return "redirect:/admin/updateAuthors/"+bookId;

    }
    @GetMapping("/book/{id}/cover")
    public ResponseEntity<byte[]> getBookCover(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null || book.getCoverImage() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] data = book.getCoverImage().getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o rileva dinamicamente
                .body(data);
    }


}
