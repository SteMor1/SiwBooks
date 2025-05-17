package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public Author getAuthorById(Long id) {
       return authorRepository.findById(id).orElse(null);
    }
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }
}
