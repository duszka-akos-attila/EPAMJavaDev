package com.epam.training.ticketservice.dataaccess.projection.compositeKey;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ScreeningCompositeKey {

    private MovieProjection movieProjection;
    private RoomProjection roomProjection;
    private Date screeningTime;
}
