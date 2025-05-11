package it.uniroma3.siwbooks.service;

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
}
