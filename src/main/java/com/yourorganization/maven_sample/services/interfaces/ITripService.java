package com.yourorganization.maven_sample.services.interfaces;


import com.yourorganization.maven_sample.entity.TripEntity;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ITripService {

    List<TripEntity> findAll();
    Optional<TripEntity> findById(Long id);

    int customInsert(TripEntity entity);
    void updateStatus(TripEntity entity, String status);
    void completeTrip(TripEntity entity);
}