package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.*;
import com.yourorganization.maven_sample.repository.*;
import com.yourorganization.maven_sample.services.interfaces.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {
    private static final Logger LOGGER = Logger.getLogger(VehicleEntity.class);
    private final VehicleRepository repository;

    @Autowired
    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VehicleEntity> findAll() { return repository.findAll(); }

    @Override
    public VehicleEntity findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void setAvailability(VehicleEntity vehicleEntity, Boolean value) { repository.setAvailability(vehicleEntity.getVehicle_id(), value); }

    @Override
    public void completeTrip(TripEntity tripEntity) {

        VehicleEntity vehicleEntity = tripEntity.getRequest_id().getVehicle_id();

        if(tripEntity.getStatus().equals("Issues") || vehicleEntity.getStatus().equals("Broken"))
           return;

        setAvailability(vehicleEntity, true);
    }

}