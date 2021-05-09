package com.epam.training.ticketservice.dataaccess.projection.compositeKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class MovieCompositeKey {

    private String movieTitle;
    private String movieGenre;

}
