package it.uniroma3.siwbooks.authentication;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.repository.CredentialsRepository;
import it.uniroma3.siwbooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private  CredentialsRepository credentialsRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        // Leggi info da Google
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");


        // Cerca credenziali esistenti
        Optional<Credentials> credentialsOpt = credentialsRepository.findByUsername(email);
        if (credentialsOpt.isEmpty()) {
            // Crea nuovo utente
            User user = new User();
            user.setFirstName(firstName != null ? firstName : name);
            user.setLastName(lastName);
            user.setEmail(email);
            Credentials credentials = new Credentials();
            credentials.setUsername(email);
            credentials.setPassword(UUID.randomUUID().toString()); //TODO AGGIUNGERE CAMPO A CREDENTIALS PER DISTINGUERE UTENTI OAUTH DA UTENTI "INTERNI"
            credentials.setRole(Credentials.DEFAULT_ROLE);
            credentials.setUser(user);
            credentialsRepository.save(credentials);
        }

        // Autorizza l'utente con il ruolo salvato
        Credentials credentials = credentialsRepository.findByUsername(email).get();
        return new CustomUserPrincipal(
                credentials.getUsername(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + credentials.getRole())),
                attributes
        );
    }
}
