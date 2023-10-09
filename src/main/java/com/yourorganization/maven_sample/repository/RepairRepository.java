package com.yourorganization.maven_sample.repository;

import com.yourorganization.maven_sample.entity.RepairEntity;
import com.yourorganization.maven_sample.entity.RequestEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO repair_requests (request_id, request_date) VALUES (:request_id, :request_date)", nativeQuery = true)
    int customInsert(@Param("request_id") Integer request_id,
                     @Param("request_date") Timestamp request_date);


    @Query("SELECT v FROM RepairEntity v where v.id = :id")
    RepairEntity findById(@Param("id") Integer id);

}
