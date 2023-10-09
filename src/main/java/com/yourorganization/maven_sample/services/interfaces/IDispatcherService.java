package com.yourorganization.maven_sample.services.interfaces;

import com.yourorganization.maven_sample.entity.*;
import com.yourorganization.maven_sample.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDispatcherService {

    HashMap<String, Integer> destinations = new HashMap<>();
    HashMap<String, Integer> cargoTypes = new HashMap<>();

    List<DriverEntity> availableDriverEntities = new ArrayList<>();
    List<VehicleEntity> availableVehicleEntities = new ArrayList<>();
    List<RequestEntity> requestEntities = new ArrayList<>();
    List<TripEntity> tripEntities = new ArrayList<>();
    List<TripEntity> newTripEntities = new ArrayList<>();


    void clearLists();
    void updateLists();
    void init(DriverService dS, VehicleService vS, TripService tS, RequestService rS, RepairService riS);
    void startProgram();
    void startDay();
    void completeTrip(TripEntity entity);
}

