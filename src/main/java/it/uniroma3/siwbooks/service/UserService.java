package it.uniroma3.siwbooks.service;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CredentialsService credentialsService;
    @Transactional
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUser();
    }
    public Credentials getCurrentUserCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return credentialsService.getCredentialsByUsername(userDetails.getUsername());
    }
}
