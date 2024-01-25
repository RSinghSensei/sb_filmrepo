package com.movCast.demo.dto;

import com.movCast.demo.Films;

public class UserDTO {

    String userName;
    Films favouriteFlick;

    public UserDTO() {}

    public UserDTO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Films getFavouriteFlick() {
        return favouriteFlick;
    }

    public void setFavouriteFlick(Films favouriteFlick) {
        this.favouriteFlick = favouriteFlick;
    }
}
