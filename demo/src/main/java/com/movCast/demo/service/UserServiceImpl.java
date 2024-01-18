package com.movCast.demo.service;

import com.movCast.demo.User;
import com.movCast.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FilmListingService filmListingService;
    @Override
    public void addUser(User newUser)
    {
        if (userRepository.findByUserName(newUser.getUsername()).isEmpty())
        {
            if (filmListingService.getFilm(newUser.getFavouriteFlick().getFilmName()).isEmpty())
            {
                System.out.println("Favourite film is incorrect."); 
                return;
            }
            System.out.println("Film name: " + filmListingService.getFilm(newUser.getFavouriteFlick().getFilmName()));
            newUser.setFavouriteFlick(filmListingService.getFilm(newUser.getFavouriteFlick().getFilmName()).get());
            userRepository.save(newUser);
        }
        else
        {
            System.out.println("Username already exists");
        }
        System.out.println("s, username : " + newUser.getUsername());
    }

    @Override
    public void updateUser(User user, boolean nameChange, String newUserName, String newFavouriteFlick)
    {
        // log
        System.out.println("namechangeval: " + " newusername: " + newUserName + " newfavflick: " + newFavouriteFlick);

        if (userRepository.findByUserName(user.getUsername()).isEmpty())
        {
            System.out.println("Username doesn't exist");
            return;
        }

        User currentUser = userRepository.findByUserName(user.getUsername()).get();

        if (nameChange)
        {
            currentUser.setUsername(newUserName);
        }
        if (filmListingService.getFilm(newFavouriteFlick).isEmpty())
        {
            currentUser.setFavouriteFlick(null);
            userRepository.save(currentUser);
            System.out.println("New FavFlick does not exist in DB");
            return;
        }
        currentUser.setFavouriteFlick(filmListingService.getFilm(newFavouriteFlick).get());
        userRepository.save(currentUser);
    }

    @Override
    public User findUser(String userName)
    {
        return userRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
