package com.yourorganization.maven_sample.repository;

import com.yourorganization.maven_sample.entity.RequestEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO requests (destination, cargo_type, cargo_quantity, driver_id, vehicle_id, cargo_weight) VALUES (:destination, :cargoType, :cargoQuantity, :driverId, :vehicleId, :cargoWeight)", nativeQuery = true)
    int customInsert(@Param("destination") String destination,
                     @Param("cargoType") String cargoType,
                     @Param("cargoQuantity") Integer cargoQuantity,
                     @Param("driverId") Integer driverId,
                     @Param("vehicleId") Integer vehicleId,
                     @Param("cargoWeight") Double cargoWeight);
}

