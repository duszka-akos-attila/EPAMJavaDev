package com.epam.training.ticketservice.dataaccess.projection;

import com.epam.training.ticketservice.dataaccess.projection.compositeKey.ScreeningCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(ScreeningCompositeKey.class)
public class ScreeningProjection {

    @Id
    @GeneratedValue
    private UUID screeningId;

    @Id
    private MovieProjection movieProjection;

    @Id
    private RoomProjection roomProjection;

    @Id
    private Date screeningTime;

}
