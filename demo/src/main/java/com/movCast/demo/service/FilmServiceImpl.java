package com.movCast.demo.service;

import com.movCast.demo.FilmRepository;
import com.movCast.demo.Filmography;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class FilmServiceImpl implements FilmService{

    @Autowired
    FilmRepository filmRepository;

    public FilmServiceImpl() {
    }

    public void addNewFilm(Filmography film)
    {
        film.setFlickRating(10);
        film.setTotalFlickRating(1);
        filmRepository.save(film);
    }

    public void removeFilmById(Integer filmId)
    {
        if (filmRepository.existsById(filmId))
        {
            filmRepository.deleteById(filmId);
        }
    }

    public void updateFilmById(Integer filmId, String updatedFlickName, String updatedFlickDirector,
                               Integer updatedFlickRating)
    {
        if (filmRepository.findById(filmId).isPresent())
        {
            Filmography currentFilm = filmRepository.findById(filmId).get();
            currentFilm.setFlickName(updatedFlickName);
            currentFilm.setFlickDirector(updatedFlickDirector);
            currentFilm.setFlickRating(updatedFlickRating);
            filmRepository.save(currentFilm);
        }
    }

    public Filmography getFilmById(Integer filmId)
    {
        return filmRepository.findById(filmId).orElse(null);
    }

    public Filmography getFilmByFlickName(String flickName)
    {
        return filmRepository.findByFlickName(flickName).orElse(null);
    }

    public void deleteFilmById(Integer filmId)
    {
        if (filmRepository.findById(filmId).isPresent())
        {
            filmRepository.deleteById(filmId);
            return;
        }
        System.out.println("Film ID doesn't exist");
    }

    public Iterable<Filmography> getAllFilms()
    {
        return filmRepository.findAll();
    }


}
