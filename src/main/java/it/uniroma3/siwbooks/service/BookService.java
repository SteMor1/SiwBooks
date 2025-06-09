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
            if(book.getCoverImage() != null) {//EVITO DI AGGIORNARE CON IMMAGINI VUOTE(PROBLEMATICA LEGATA AL FORM)
                bookToUpdate.setCoverImage(book.getCoverImage());
            }
            if(!book.getBookImages().isEmpty()) {//EVITO DI AGGIORNARE CON IMMAGINI VUOTE(PROBLEMATICA LEGATA AL FORM)
                bookToUpdate.setBookImages(book.getBookImages());
            }
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


    public Boolean bookExistsByTitleAndYear(String title,Integer year) {
        return bookRepository.existsByTitleAndPublicationYear(title,year);
    }
    public Iterable<Book> findBooksByCriteria(String title,String author,Integer yearFrom, Integer yearTo){
        
        return bookRepository.findByCriteria(title,author, yearFrom,yearTo);
    }
}
