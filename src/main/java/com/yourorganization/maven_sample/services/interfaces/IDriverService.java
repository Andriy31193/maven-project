package com.yourorganization.maven_sample.services.interfaces;

import com.yourorganization.maven_sample.entity.DriverEntity;
import com.yourorganization.maven_sample.entity.TripEntity;
import com.yourorganization.maven_sample.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface IDriverService {

    List<DriverEntity> findAll();
    Optional<DriverEntity> findById(Long id);
    void setAvailability(DriverEntity entity, Boolean value);

    void completeTrip(TripEntity tripEntity);
}
