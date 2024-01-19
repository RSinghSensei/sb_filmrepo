package com.movCast.demo.service;

import com.movCast.demo.FilmListingRepository;
import com.movCast.demo.Films;
import com.movCast.demo.User;
import com.movCast.demo.dto.FilmListingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmListingServiceImpl implements FilmListingService {

    @Autowired
    FilmListingRepository filmListingRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public void saveNewFilm(Films newFilm)
    {
        if (filmListingRepository.findByFilmName(newFilm.getFilmName()).isEmpty())
        {
            filmListingRepository.save(newFilm);
        }
    }
    @Override
    public void updateCurrentFilm(Films film, String newFilmName)
    {
        Films currentFilm = filmListingRepository.findByFilmName(film.getFilmName()).orElse(null);
        if (currentFilm == null)
        {
            System.out.println("Film does not exist");
            return;
        }
        currentFilm.setFilmName(newFilmName);
        filmListingRepository.save(currentFilm);
    }
    @Override
    public void deleteCurrentFilm()
    {

    }
    @Override
    public Optional<FilmListingDTO> getFilm(String filmName)
    {
        if (filmListingRepository.findByFilmName(filmName).isPresent())
        {
            return Optional.of(modelMapper.map(filmListingRepository.findByFilmName(filmName), FilmListingDTO.class));
        }
        return Optional.empty();
    }


    @Override
    public Iterable<FilmListingDTO> getAllFilms()
    {
        List<FilmListingDTO>currentFilmListings = new ArrayList<>();

        for (Films films: filmListingRepository.findAll())
        {
            currentFilmListings.add(modelMapper.map(films, FilmListingDTO.class));
        }

        return currentFilmListings;
    }
    @Override
    public Iterable<User> getUserList(Films currentFilm)
    {
        return currentFilm.getUserList();
    }
}
