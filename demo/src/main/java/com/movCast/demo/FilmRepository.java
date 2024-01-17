package com.movCast.demo;

import org.springframework.data.repository.CrudRepository;

import com.movCast.demo.Filmography;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface FilmRepository extends CrudRepository<Filmography, Integer> {
    Optional<Filmography> findByFlickName(String flickName);
}
