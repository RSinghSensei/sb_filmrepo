package com.movCast.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
