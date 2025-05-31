package it.uniroma3.siwbooks.service;



import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepository;

    public Credentials getCredentialsByUsername(Long id) {
        return credentialsRepository.findById(id).orElse(null);
    }
    public Credentials getCredentialsByUsername(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return credentialsRepository.save(credentials);
    }
    public Boolean credentialsExistsByUsername(String username) {
        return credentialsRepository.findByUsername(username).isPresent();
    }
}
