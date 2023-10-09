package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.RequestEntity;
import com.yourorganization.maven_sample.repository.RequestRepository;
import com.yourorganization.maven_sample.services.interfaces.IRequestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements IRequestService {
    private static final Logger LOGGER = Logger.getLogger(RequestEntity.class);
    private final RequestRepository repository;

    @Autowired
    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RequestEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RequestEntity> findById(Long id) {
        return repository.findById(id);
    }

    public int customInsert(String destination,
                            String cargoType,
                            Integer cargoQuantity,
                            Integer driverId,
                            Integer vehicleId,
                            Double cargoWeight)
    {
        return repository.customInsert(destination, cargoType, cargoQuantity, driverId, vehicleId, cargoWeight);
    }

}