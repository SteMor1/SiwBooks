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
        Book bookToUpdate = bookRepository.findById(book.getId()).orElse(null);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setPublicationDate(book.getPublicationDate());
            return bookRepository.save(bookToUpdate);
        }
        return null;
    }
    public void deleteBook(Long id) {
        Book bookToDelete = getBookById(id);
        for (Author author : bookToDelete.getAuthors()) {
            author.getBooks().remove(bookToDelete);
        }
        bookRepository.deleteById(id);
    }
    public void addAuthorToBook(Long authorId, Long bookId) {
        bookRepository.addAuthorToBook(authorId, bookId);
    }
    public void removeAuthorFromBook(Long authorId, Long bookId) {
        bookRepository.removeAuthorFromBook(authorId, bookId);
    }
    public Iterable<Book> findByTitleStartingWith(String title) {
        return bookRepository.findBookByTitleStartingWith(title);
    }
    public Iterable<Book> findByAuthor(String author) {
        return bookRepository.findBookByAuthor(author);
    }
    public Iterable<Book> findByYear(Integer yearFrom, Integer yearTo) {
        return bookRepository.findBookByYear(yearFrom,yearTo);
    }
    public Boolean bookExistsByTitleAndAuthors(String title, Iterable<Author> authors) {
        return bookRepository.existsByTitleAndAuthors(title,authors);
    }
}
