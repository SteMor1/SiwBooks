package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
