package com.yourorganization.maven_sample.services.interfaces;


import com.yourorganization.maven_sample.entity.TripEntity;
import com.yourorganization.maven_sample.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface IVehicleService {

    List<VehicleEntity> findAll();
    VehicleEntity findById(Integer id);

    void setAvailability(VehicleEntity vehicleEntity, Boolean value);
    void completeTrip(TripEntity tripEntity);
}
