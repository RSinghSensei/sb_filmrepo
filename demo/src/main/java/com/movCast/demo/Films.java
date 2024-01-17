package com.movCast.demo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FilmList")
public class Films {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public String toString() {
        return "Films{" +
                "id=" + id +
                ", FilmName='" + filmName + '\'' +
                ", UserList=" + UserList +
                '}';
    }

    @Column(name = "filmName")
    private String filmName;

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String currentFilmName) {
        filmName = currentFilmName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "FavouriteFlick")
    private Set<User> UserList = new HashSet<>();

    public Set<User> getUserList() {
        return UserList;
    }

    public void setUserList(Set<User> userList) {
        UserList = userList;
    }
}
