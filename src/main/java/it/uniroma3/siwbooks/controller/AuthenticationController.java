package it.uniroma3.siwbooks.controller;



import it.uniroma3.siwbooks.controller.validator.CredentialsValidator;
import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.CredentialsService;
import it.uniroma3.siwbooks.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsValidator credentialsValidator;
    @Autowired
    private BookService bookService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("credentials",new Credentials());
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }


    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
        return "redirect:/";
    }
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model , @ModelAttribute("confirm") String confirmPassword) {
        // TODO VALIDAZIONE

        //TODO VALUTARE SE DIVIDERE USER E CREDENTIALS
        this.credentialsValidator.validate(credentials, credentialsBindingResult);
        if(credentialsBindingResult.hasErrors()) {
            return "register";
        }
        if(!confirmPassword.equals(credentials.getPassword())) {
            model.addAttribute("error", "Passwords do not match"); //TODO MOSTRARE ERRORE
            return register(model);
        }
        credentialsService.saveCredentials(credentials);
        return "redirect:login";
    }
    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("popularBooks",bookService.getAllBooks());//TODO ORDINO IN BASE ALLE RECENSIONI
            return "index.html";
        }
        else {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "admin/indexAdmin.html";
            }
        }
        model.addAttribute("popularBooks",bookService.getAllBooks());//TODO ORDINO IN BASE ALLE RECENSIONI
        return "index.html";
    }

}

