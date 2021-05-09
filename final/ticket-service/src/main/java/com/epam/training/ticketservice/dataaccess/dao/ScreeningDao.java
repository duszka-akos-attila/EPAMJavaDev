package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.dataaccess.projection.compositeKey.ScreeningCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScreeningDao extends JpaRepository<ScreeningProjection, UUID> {

    Optional<ScreeningProjection> findByScreeningCompositeKey(ScreeningCompositeKey compositeKey);
}
