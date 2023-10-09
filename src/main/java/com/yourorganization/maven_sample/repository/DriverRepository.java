package com.yourorganization.maven_sample.repository;

import com.yourorganization.maven_sample.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE DriverEntity SET available = :available where driver_id = :driver_id")
    void setAvailability(@Param("driver_id") Integer driver_id, @Param("available") Boolean available);
}