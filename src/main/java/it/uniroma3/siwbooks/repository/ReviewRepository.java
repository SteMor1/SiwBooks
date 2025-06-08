package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public boolean existsByBookIdAndAuthorId(Long bookId, Long authorId);

    List<Review> findByBookId(Long bookId);
    @Query(nativeQuery=true, value = " SELECT avg(rating) FROM review r where r.book_id=:bookId")
    float getAverageRatingForBook(@Param("bookId") Long bookId);
}
