package it.uniroma3.siwbooks.controller.validator;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorValidator implements Validator {
    @Autowired
    private AuthorService authorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
       if(author.getFirstName()!=null && author.getLastName()!=null && !author.getFirstName().isEmpty() && !author.getLastName().isEmpty() && authorService.authorExistsByNameAndLastNameAndDateOfBirth(author.getFirstName(), author.getLastName(), author.getDateOfBirth())) {
           errors.reject("author.duplicate");
       }
       if(author.getDateOfBirth()!=null&&author.getDateOfDeath()!=null) {
           if(author.getDateOfDeath().isBefore(author.getDateOfBirth())) {
               errors.rejectValue("dateOfDeath","author.dateOfDeath.beforeBirth");
               errors.rejectValue("dateOfBirth","author.dateOfBirth.afterDeath");
           }
       }

    }
}
