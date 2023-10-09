package com.yourorganization.maven_sample.services.interfaces;

import com.yourorganization.maven_sample.entity.RequestEntity;

import java.util.List;
import java.util.Optional;

public interface IRequestService {

    List<RequestEntity> findAll();
    Optional<RequestEntity> findById(Long id);
}
