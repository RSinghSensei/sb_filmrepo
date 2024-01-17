package com.movCast.demo.service;

import com.movCast.demo.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public void addUser(User newUser);
    public void updateUser(User user, boolean nameChange, String newUserName, String newFavouriteFlick);
    public User findUser(String userName);
    public Iterable<User> getAllUsers();

}
