package com.movCast.demo.service;

import com.movCast.demo.User;
import com.movCast.demo.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public void addUser(UserDTO userDTO);
    public void updateUser(UserDTO userDTO, boolean nameChange, String newUserName, String newFavouriteFlick);
    public UserDTO findUser(String userName);
    public Iterable<UserDTO> getAllUsers();

}
