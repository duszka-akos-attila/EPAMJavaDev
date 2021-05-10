package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieDao extends JpaRepository<MovieProjection,Long> {

    Optional<MovieProjection> findByMovieTitle(String movieTitle);

    void deleteByMovieTitle(String movieTitle);
    
}
