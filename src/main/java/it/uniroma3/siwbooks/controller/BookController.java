package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.controller.validator.BookValidator;
import it.uniroma3.siwbooks.model.*;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.ReviewService;
import it.uniroma3.siwbooks.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookValidator bookValidator;

    @GetMapping("/book")
    public String showBooks(@RequestParam(required = false) String title,@RequestParam(required = false) String author, @RequestParam(required = false) Integer yearFrom, @RequestParam(required = false) Integer yearTo, Model model) {

        model.addAttribute("books",bookService.findBooksByCriteria(title, author, yearFrom, yearTo));
        return "books";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        List<Review> bookReviews = reviewService.getBookReviews(id);
        User currentUser = userService.getCurrentUser();
        Review userReview = null;
        if(currentUser != null) {
            userReview = bookReviews.stream()
                    .filter(review -> review.getAuthor().equals(currentUser))
                    .findFirst().orElse(null); //OTTENGO REVIEW UTENTE
        }
        model.addAttribute("userReview", userReview);
        model.addAttribute("bookReviews", bookReviews.stream().filter(review -> !review.getAuthor().equals(currentUser)));// INVIO SOLO QUELLE CHE NON SONO DELL'UTENTE forse migliorabile

        model.addAttribute("book",bookService.getBookById(id));
        return "book";
    }
    @GetMapping("/admin/formNewBook")
    public String formNewBook(Model model) {
        model.addAttribute("authors",authorService.getAllAuthors());
        model.addAttribute("book", new Book());
        return "admin/formNewBook";
    }
    @PostMapping("/admin/book")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bookBindingResult, @RequestParam("bookCover") MultipartFile bookCoverUpload, @RequestParam("otherImages") MultipartFile[] bookImagesUpload,Model model) {

        bookValidator.validate(book, bookBindingResult);
        if(bookBindingResult.hasErrors()) {
            model.addAttribute("authors",authorService.getAllAuthors());
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
    public String updateAuthor(@ModelAttribute("book") Book book, @RequestParam("bookCover") MultipartFile bookCoverUpload,@RequestParam("otherImages") MultipartFile[] bookImagesUpload, Model model) {
        Image bookCover = new Image();
        List<Image> bookImages = new ArrayList <Image>();
        if(!bookCoverUpload.isEmpty()) {
            try {
                bookCover.setData(bookCoverUpload.getBytes());
                book.setCoverImage(bookCover);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(bookImagesUpload.length > 0) {
            System.out.println("Immagini da aggiungere: "+bookImagesUpload.length);
            for (MultipartFile file : bookImagesUpload) {
                if (!file.isEmpty()) {
                    Image img = new Image();
                    try {
                        img.setData(file.getBytes());
                        bookImages.add(img);
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
            book.setBookImages(bookImages);
        }

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
    public String addAuthor(@PathVariable("bookId") Long bookId, @PathVariable("authorId") Long authorId, Model model) {

        this.bookService.addAuthorToBook(authorId,bookId);
        Book book = this.bookService.getBookById(bookId);
        Iterable<Author> availableAuthors = authorService.findAuthorsNotInBook(bookId);
        model.addAttribute("availableAuthors", availableAuthors);
        return "redirect:/admin/updateAuthors/"+bookId;
    }

    @GetMapping("/admin/removeAuthorFromBook/{authorId}/{bookId}")
    public String removeAuthor(@PathVariable("authorId") Long authorId, @PathVariable("bookId") Long bookId, Model model) {

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
    @GetMapping("/book/{book_id}/images/{img_id}")
    public ResponseEntity<byte[]> getBookImage(@PathVariable("book_id") Long book_id,@PathVariable("img_id") Integer img_id) {
        Book book = bookService.getBookById(book_id);

        if (book == null || img_id>=book.getBookImages().size()) {

            return ResponseEntity.notFound().build();
        }

        byte[] data = book.getBookImages().get(img_id).getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o rileva dinamicamente
                .body(data);
    }


}
