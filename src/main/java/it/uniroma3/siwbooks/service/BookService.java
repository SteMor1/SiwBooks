package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Long id) {
        Book bookToDelete = getBookById(id);
        for (Author author : bookToDelete.getAuthors()) {
            author.getBooks().remove(bookToDelete);
        }
        bookRepository.deleteById(id);
    }
}
