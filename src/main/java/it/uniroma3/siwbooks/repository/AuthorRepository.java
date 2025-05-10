package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
