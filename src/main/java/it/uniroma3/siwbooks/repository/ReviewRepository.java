package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public boolean existsByBookIdAndAuthorId(Long bookId, Long authorId);
}
