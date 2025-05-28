package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery=true, value = "INSERT into book_authors (authors_id, books_id) VALUES (:authorId, :bookId)")
    public void addAuthorToBook(@Param("authorId") Long authorId, @Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(nativeQuery=true, value = "DELETE from book_authors where authors_id= :authorId AND books_id= :bookId")
    public void removeAuthorFromBook(@Param("authorId") Long authorId, @Param("bookId") Long bookId);

    @Query(nativeQuery=true, value = "SELECT * from book b where lower(b.title) LIKE lower(concat(:title,'%'))")
    public Iterable<Book> findBookByTitleStartingWith(@Param("title") String title);
}
