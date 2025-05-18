package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
