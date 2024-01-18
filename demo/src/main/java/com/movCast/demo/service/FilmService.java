package com.movCast.demo.service;

import com.movCast.demo.Filmography;

public interface FilmService {

    public void addNewFilm(Filmography film);
    public void removeFilmById(Integer filmId);
    public void updateFilmById(Integer filmId,  String updatedFlickName, String updatedFlickDirector,
                               Integer updatedFlickRating);
    public Filmography getFilmById(Integer filmId);
    public Filmography getFilmByFlickName(String flickName);
    public void deleteFilmById(Integer filmId);
    public Iterable<Filmography> getAllFilms();

}
