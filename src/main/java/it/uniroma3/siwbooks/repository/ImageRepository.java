package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Image;

import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
