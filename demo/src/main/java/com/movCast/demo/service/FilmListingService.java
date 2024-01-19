package com.movCast.demo.service;

import com.movCast.demo.Films;
import com.movCast.demo.User;
import com.movCast.demo.dto.FilmListingDTO;

import java.util.List;
import java.util.Optional;

public interface FilmListingService {

    public void saveNewFilm(Films newFilm);
    public void updateCurrentFilm(Films film, String newFilmName);
    public void deleteCurrentFilm();
    public Optional<FilmListingDTO> getFilm(String filmName);
    public Iterable<FilmListingDTO> getAllFilms();
    public Iterable<User> getUserList(Films currentFilm);

}
