package it.uniroma3.siwbooks.controller.validator;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.service.CredentialsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class CredentialsValidator implements Validator {
    @Autowired
    private CredentialsService credentialsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Credentials credentials = (Credentials) target;
        if(credentials.getPassword() == null || credentials.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.required");
        }if(credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
            errors.rejectValue("username", "username.required");
        }
        if(credentials.getUsername() != null && credentialsService.credentialsExistsByUsername(credentials.getUsername())) {
            errors.rejectValue("username", "username.exists");
        }

    }
}
