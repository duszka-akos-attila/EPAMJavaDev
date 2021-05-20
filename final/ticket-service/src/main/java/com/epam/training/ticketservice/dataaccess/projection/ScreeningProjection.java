package com.epam.training.ticketservice.dataaccess.projection;

import com.epam.training.ticketservice.dataaccess.projection.compositekey.ScreeningCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ScreeningProjection {

    @EmbeddedId
    private ScreeningCompositeKey screeningCompositeKey;

}
