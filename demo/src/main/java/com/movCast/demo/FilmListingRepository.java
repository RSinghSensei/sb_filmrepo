package com.movCast.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FilmListingRepository extends CrudRepository<Films, Integer> {
//    @Query(value = "SELECT film_name from film_list f WHERE f.film_name = ?1", nativeQuery = true)

    Optional<Films> findByFilmName(String currentFilmName);
}
