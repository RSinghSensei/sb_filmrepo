package com.movCast.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movCast.demo.FilmListingRepository;
import com.movCast.demo.Films;
import com.movCast.demo.User;
import com.movCast.demo.dto.FilmListingDTO;
import com.movCast.demo.dto.PaginatedFilmListingDTO;
import io.netty.handler.codec.Headers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmListingServiceImpl implements FilmListingService {

    @Autowired
    FilmListingRepository filmListingRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    WebClient webClient;


    @Override
    public void saveNewFilm(FilmListingDTO newFilm)
    {
        if (filmListingRepository.findByFilmName(newFilm.getFilmName()).isEmpty())
        {
            Films newFilmListing = modelMapper.map(newFilm, Films.class);
            System.out.println("New Film Detes: " + newFilmListing.getFilmName() + " current user set: " + newFilmListing.getUserList());
            filmListingRepository.save(newFilmListing);
            return;
        }
        System.out.println("Film already exists in repo");
    }

    @Override
    public void addNewFilmFromAPI()
    {
        Mono<List<FilmListingDTO>> filmPayload = webClient.get().
                uri("/discover/movie?include_adult=false&include_video=false&language=en-US&page=2&sort_by=popularity.desc&year=2023").
                headers(httpHeaders -> httpHeaders.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5OTI4Y2I0Mzk4MmUyZDFkMmRhNzMzYWExZmQ4ZTU2NiIsInN1YiI6IjY1YWU5YzlhMjVjZDg1MDEwYTZlOThjNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VQyEaQg1Z97qABCDkaz4e2U9g-jse7MdftE7VeFNpvE")).
                retrieve().
                bodyToMono(PaginatedFilmListingDTO.class).
                map(PaginatedFilmListingDTO::getResults);
        List<FilmListingDTO> objects = filmPayload.block();
        for (FilmListingDTO film: objects){System.out.println(film.getFilmName()); saveNewFilm(film);}
        for (Films films: filmListingRepository.findAll()){System.out.print(films.getFilmName() + " ");}
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Films> filmsList = Arrays.stream(objects).map()
//        System.out.println(objects.stream().map(Films::getFilmName).collect(Collectors.toList()));
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
    public Optional<Films> getFilmObj(String filmName)
    {
        if (filmListingRepository.findByFilmName(filmName).isPresent())
        {
            return filmListingRepository.findByFilmName(filmName);
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
