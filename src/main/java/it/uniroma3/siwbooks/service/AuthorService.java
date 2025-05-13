package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public Author getAuthorById(Long id) {
       return authorRepository.findById(id).orElse(null);
    }
}
