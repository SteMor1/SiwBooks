package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Author a= authorRepository.findById(id).orElse(null);
        if(a!=null) {
            for(Book b : a.getBooks()){
                b.getAuthors().remove(a);
            }
        }
        authorRepository.deleteById(id);


    }
    public Author updateAuthor(Author author) {
        Author authorToUpdate = authorRepository.findById(author.getId()).orElse(null);
        if(authorToUpdate!=null) {
            if(author.getPicture()!=null) {
                authorToUpdate.setPicture(author.getPicture());//EVITO DI AGGIORNARE CON IMMAGINI VUOTE(PROBLEMATICA LEGATA AL FORM)
            }
            authorToUpdate.setDateOfBirth(author.getDateOfBirth());
            authorToUpdate.setFirstName(author.getFirstName());
            authorToUpdate.setLastName(author.getLastName());
            authorToUpdate.setDateOfDeath(author.getDateOfDeath());
            return authorRepository.save(authorToUpdate);
        }
       return null;
    }
    public List<Author> findAuthorsNotInBook(Long bookId) {
        return authorRepository.findAuthorsNotInBook(bookId);
    }

    public Boolean authorExistsByNameAndLastNameAndDateOfBirth(String authorName, String lastName, LocalDate dateOfBirth) {
        return authorRepository.existsByFirstNameAndLastNameAndDateOfBirth(authorName, lastName, dateOfBirth);
    }
    public List<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }
}
