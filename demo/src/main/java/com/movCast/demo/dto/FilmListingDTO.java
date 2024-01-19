package com.movCast.demo.dto;


import com.movCast.demo.User;

import java.util.Set;

public class FilmListingDTO {
    String filmName;
    Set<User> userList;


    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public FilmListingDTO() {
    }
}
