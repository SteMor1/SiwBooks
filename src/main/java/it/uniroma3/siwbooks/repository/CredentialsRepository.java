package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    public Optional<Credentials> findByUsername(String username);
}

