package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/author/{id}")
    public String showAuthor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "author";
    }
    @GetMapping("/author/{id}/photo")
    public ResponseEntity<byte[]> getAuthorPhoto(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        if (author == null || author.getPicture() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] data = author.getPicture().getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o rileva dinamicamente
                .body(data);
    }
}
