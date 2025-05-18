package it.uniroma3.siwbooks.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthenticationController {
    @GetMapping(value = "/admin")
    public String index(Model model) {

        return "admin/indexAdmin.html";
    }
}
