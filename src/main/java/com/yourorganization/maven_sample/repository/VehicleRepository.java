package com.yourorganization.maven_sample.repository;

import com.yourorganization.maven_sample.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query("SELECT v FROM VehicleEntity v WHERE v.vehicle_id = :vehicle_id")
    VehicleEntity findById(@Param("vehicle_id") Integer vehicle_id);

    @Modifying
    @Transactional
    @Query("UPDATE VehicleEntity SET available = :available WHERE vehicle_id = :vehicle_id")
    void setAvailability(@Param("vehicle_id") Integer vehicle_id, @Param("available") Boolean available);
}
