package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    @Column(name = "id")
    private Long movieId;

    @Column(unique = true)
    private String movieTitle;

    private String movieGenre;

    private int movieLength;
}
