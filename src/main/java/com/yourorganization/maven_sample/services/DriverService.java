package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.*;
import com.yourorganization.maven_sample.repository.DriverRepository;
import com.yourorganization.maven_sample.services.interfaces.IDriverService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IDriverService {
    private static final Logger LOGGER = Logger.getLogger(DriverEntity.class);
    private final DriverRepository repository;

    @Autowired
    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DriverEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DriverEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void setAvailability(DriverEntity entity, Boolean value) { repository.setAvailability(entity.getDriver_id(), value); }

    @Override
    public void completeTrip(TripEntity tripEntity) {

        RequestEntity requestEntity = tripEntity.getRequest_id();
        DriverEntity driverEntity = requestEntity.getDriver_id();

        setAvailability(driverEntity, true);

        LOGGER.info("Driver " + driverEntity.getName() + " has finished the request (Request ID: " + requestEntity.getRequest_id());
    }
}