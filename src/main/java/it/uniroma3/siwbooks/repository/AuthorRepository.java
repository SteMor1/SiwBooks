package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Query(nativeQuery = true , value = "select * from author a where a.id not in (select book_authors.authors_id from book_authors where book_authors.books_id = :bookId  )")
    public List<Author> findAuthorsNotInBook(@Param("bookId") Long bookId );

    boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

    @Query(nativeQuery = true , value = "select * from author a where (concat(lower(a.first_name),' ',lower(a.last_name)) LIKE lower(concat(:author,'%')) OR (concat(lower(a.last_name),' ',lower(a.first_name)) LIKE lower(concat(:author,'%'))))")
    List<Author> findByName(@Param("author")String name);
}
