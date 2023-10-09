package com.yourorganization.maven_sample.repository;

import com.yourorganization.maven_sample.entity.TripEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE TripEntity SET status = :status WHERE trip_id = :trip_id")
    void setTripStatus(@Param("trip_id") Integer trip_id, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO trips (request_id, start_date, end_date, total_payment) VALUES (:request_id, :start_date, :end_date, :total_payment)", nativeQuery = true)
    int customInsert(@Param("request_id") Integer request_id,
                     @Param("start_date") Timestamp start_date,
                     @Param("end_date") Timestamp end_date,
                     @Param("total_payment") Double total_payment);

}
