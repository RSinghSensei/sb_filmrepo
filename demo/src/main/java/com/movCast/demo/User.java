package com.movCast.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    public User() {
    }

    @Column(name = "user_name", nullable = false, length = 32)
    private String userName;

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        userName = username;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filmID", referencedColumnName = "id")
    private Films FavouriteFlick;

    public Films getFavouriteFlick() {
        return FavouriteFlick;
    }

    public void setFavouriteFlick(Films favouriteFlick) {
        FavouriteFlick = favouriteFlick;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
