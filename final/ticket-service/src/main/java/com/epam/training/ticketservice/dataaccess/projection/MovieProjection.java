package com.epam.training.ticketservice.dataaccess.projection;

import com.epam.training.ticketservice.dataaccess.projection.compositeKey.MovieCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class MovieProjection {

    @Id
    @GeneratedValue
    private long movieId;

    @EmbeddedId
    private MovieCompositeKey movieCompositeKey;

    private int movieLength;
}
