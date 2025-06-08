package it.uniroma3.siwbooks.controller;

import it.uniroma3.siwbooks.controller.validator.AuthorValidator;
import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.Image;
import it.uniroma3.siwbooks.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorValidator authorValidator;

    @GetMapping("/author")
    public String showAuthor(@RequestParam(required = false) String authorName,Model model) {
        Iterable<Author> authors ;
        if (authorName != null) {
            authors = authorService.findByName(authorName);
        }else {
            authors=authorService.getAllAuthors();
        }
        model.addAttribute("authors", authors);

        return "authors";
    }
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
    @GetMapping("/admin/formNewAuthor")
    public String formNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor";
    }
    @PostMapping("/admin/author")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult authorBindingresults, @RequestParam("imageFile") MultipartFile imageFile) {

        Image picture = new Image();
        try {
            picture.setData(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        author.setPicture(picture);
        authorValidator.validate(author, authorBindingresults);
        if(authorBindingresults.hasErrors()) {//TODO provare a rendere il controllo sulla duplicazione case-insensitive
            return "admin/formNewAuthor";
        }
        authorService.saveAuthor(author);
        return "redirect:/author/" + author.getId();
    }
    @GetMapping("/admin/indexAuthor")
    public String indexAuthor(@RequestParam(required = false) String authorName,Model model) {
        Iterable<Author> authors ;
        if (authorName != null) {
            authors = authorService.findByName(authorName);
        }else {
            authors=authorService.getAllAuthors();
        }
        model.addAttribute("authors", authors);
        return "admin/indexAuthor";
    }
    @GetMapping("/admin/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable("id") Long id,Model model) {

        authorService.deleteAuthor(id);
        return "redirect:/admin/indexAuthor";
    }
    @GetMapping("/admin/formUpdateAuthor/{id}")
    public String updateAuthor(@PathVariable("id") Long id,Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "admin/formUpdateAuthor";
    }
    @PostMapping("/admin/updateAuthor")
    public String updateAuthor(@ModelAttribute("author") Author author, Model model,@RequestParam("imageFile") MultipartFile imageFile) {
        Image picture = new Image();
        try {
            picture.setData(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        author.setPicture(picture);
        authorService.updateAuthor(author);
        return "redirect:/admin/indexAuthor";
    }


}
