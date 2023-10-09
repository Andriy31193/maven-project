package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.RepairEntity;
import com.yourorganization.maven_sample.repository.RepairRepository;
import com.yourorganization.maven_sample.services.interfaces.IRepairService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RepairService implements IRepairService {
    private static final Logger LOGGER = Logger.getLogger(RepairService.class);
    private final RepairRepository repository;

    @Autowired
    public RepairService(RepairRepository repository) {
        this.repository = repository;
    }

    @Override
    public RepairEntity findById(Integer id) {
        return repository.findById(id);
    }

    public int customInsert(Integer requestId, Timestamp time) { return repository.customInsert(requestId, time); }
}
