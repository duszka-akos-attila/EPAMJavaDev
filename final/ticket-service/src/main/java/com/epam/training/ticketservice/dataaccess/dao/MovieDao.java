package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.compositeKey.MovieCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieDao extends JpaRepository<MovieProjection,Long> {

    Optional<MovieProjection> findByMovieCompositeKey(MovieCompositeKey compositeKey);
}
