package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
