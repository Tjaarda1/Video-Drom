package com.videodrom.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

    protected enum Genre {
        HORROR,
        COMEDY,
        INDIE
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(unique = true, updatable = false)
    private String uniqueKey;
  
    @PrePersist
    private void onCreateAbstractBaseEntity() {
        
        this.uniqueKey = this.title.replace(" ", "_");
        
        // String uniqueTitle= this.title + String.valueOf(Math.random() * 1000);
        // this.uniqueKey= Base64.getEncoder().encodeToString(uniqueTitle.getBytes() );
    }
    
    @Column
    private String title;
    
    @Column(length = 1000)
    private String description;

    @Column
    private Genre genre;



    public Movie() {
        }

    public Movie(String title, String description, Genre genre) {
            this.title = title;
            this.description = description;
            this.genre = genre;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }


  
}
