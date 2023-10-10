package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.TripEntity;
import com.yourorganization.maven_sample.repository.TripRepository;
import com.yourorganization.maven_sample.services.interfaces.ITripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService implements ITripService {

    private static final Logger LOGGER = Logger.getLogger(TripService.class);
    private final TripRepository repository;

    @Autowired
    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TripEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TripEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public int customInsert(TripEntity entity) { return repository.customInsert(entity.getRequest_id().getRequest_id(), entity.getStart_date(), entity.getEnd_date(), entity.getTotal_payment()); }

    @Override
    public void updateStatus(TripEntity entity, String status)
    {
        entity.setStatus(status);
        repository.setTripStatus(entity.getTrip_id(), status);
    }

    @Override
    public void completeTrip(TripEntity entity) { updateStatus(entity,"Completed"); }
}