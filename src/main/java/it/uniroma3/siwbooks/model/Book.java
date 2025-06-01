package it.uniroma3.siwbooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private LocalDate publicationDate;
    @ManyToMany
    private List<Author> authors;
    @OneToMany(mappedBy = "book",cascade = CascadeType.REMOVE)
    private List<Review> reviews;
    @OneToOne(cascade = CascadeType.ALL)
    private Image coverImage;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> bookImages;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Image> getBookImages() {
        return bookImages;
    }

    public void setBookImages(List<Image> bookImages) {
        this.bookImages = bookImages;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }
}
