package com.movCast.demo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movCast.demo.User;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmListingDTO {
    @JsonProperty("original_title")
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
