package com.movCast.demo.service;

import com.movCast.demo.Films;
import com.movCast.demo.User;

import java.util.List;
import java.util.Optional;

public interface FilmListingService {

    public void saveNewFilm(Films newFilm);
    public void updateCurrentFilm(Films film, String newFilmName);
    public void deleteCurrentFilm();
    public Optional<Films> getFilm(String filmName);
    public Iterable<Films> getAllFilms();
    public Iterable<User> getUserList(Films currentFilm);

}
