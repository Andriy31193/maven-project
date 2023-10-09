package com.yourorganization.maven_sample.services.interfaces;

import com.yourorganization.maven_sample.entity.RepairEntity;

public interface IRepairService {

    RepairEntity findById(Integer id);

}
