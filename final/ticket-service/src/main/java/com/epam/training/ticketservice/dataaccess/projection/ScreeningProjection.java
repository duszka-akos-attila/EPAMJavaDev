package com.epam.training.ticketservice.dataaccess.projection;

import com.epam.training.ticketservice.dataaccess.projection.compositeKey.ScreeningCompositeKey;

import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public class ScreeningProjection {

    @Id
    @GeneratedValue
    private UUID screeningId;

    @EmbeddedId
    private ScreeningCompositeKey screeningCompositeKey;

}
