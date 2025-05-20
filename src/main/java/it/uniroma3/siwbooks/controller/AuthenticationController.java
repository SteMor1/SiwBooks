package it.uniroma3.siwbooks.controller;



import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.service.CredentialsService;
import it.uniroma3.siwbooks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;

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
        if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
    }
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model ,@ModelAttribute("confirm") String confirmPassword) {
        // TODO VALIDAZIONE

        //TODO VALUTARE SE DIVIDERE USER E CREDENTIALS

        if(!confirmPassword.equals(credentials.getPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return register(model);
        }
        credentialsService.saveCredentials(credentials);
        return "redirect:login";
    }
    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "index.html";
        }
        else {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "admin/indexAdmin.html";
            }
        }
        return "index.html";
    }

}

