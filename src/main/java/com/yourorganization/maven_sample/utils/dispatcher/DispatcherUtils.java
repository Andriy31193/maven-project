package com.yourorganization.maven_sample.utils.dispatcher;

import com.yourorganization.maven_sample.entity.*;
import com.yourorganization.maven_sample.services.*;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DispatcherUtils {
    private static final Logger LOGGER = Logger.getLogger(DispatcherUtils.class);
    // Simple generation method
    public static List<RequestEntity> gRandomRequests(List<RequestEntity> requestEntities, HashMap<String, Integer> destinations, HashMap<String, Integer> cargoTypes, int size) {
        List<RequestEntity> newRequestEntities = new ArrayList<>();

        try {
            Random random = new Random();

            if (size == 0)
                size = random.nextInt(5);

            int lastId = 0;
            if (requestEntities.size() > 0)
                lastId = requestEntities.get(requestEntities.size() - 1).getRequest_id();

            if (destinations.isEmpty() || cargoTypes.isEmpty())
                throw new Exception("### Destinations or cargo types array is empty. Fill arrays with correct data and try again.");

            for (int i = 0; i < size; i++, lastId++) {
                int id = lastId + 1;

                String destination = (String) destinations.keySet().toArray()[random.nextInt(destinations.size())];
                String cargoType = (String) cargoTypes.keySet().toArray()[random.nextInt(cargoTypes.size())];
                int cargoQuantity = random.nextInt(10);
                double cargoWeight = 100 + random.nextInt(2000);
                DriverEntity driverEntity = new DriverEntity();
                VehicleEntity vehicleEntity = new VehicleEntity();


                RequestEntity requestEntity = new RequestEntity(id, destination, cargoType, cargoQuantity, driverEntity, vehicleEntity, cargoWeight);
                newRequestEntities.add(requestEntity);

            }

            return newRequestEntities;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return newRequestEntities;
    }

    public static void processTripStatus(VehicleEntity ev, TripEntity te) {

        if (ev.getStatus().equals("Broken"))
            return;

        Random random = new Random();
        int randValue = random.nextInt(10);

        // Emulation of car broke down situation
        if (randValue <= 1) {
            ev.setStatus("Broken");
            te.setStatus("Issues");

        }

    }
    public static void requestVehicleRepair(RepairService repairService, LocalDateTime time, Integer requestId) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(time.format(dateFormat));

        repairService.customInsert(requestId, timestamp);

        LOGGER.info("Repair entity has been created. Request id: "+ requestId);
    }
    public static DriverEntity findDriverWithExperience(List<DriverEntity> entities, int requiredExperience) { return entities.stream().filter(DriverEntity::getAvailable).filter(driverEntity -> driverEntity.getExperience() >= requiredExperience).findFirst().orElse(null); }
    public static boolean isRequestExists(final List<RequestEntity> list, final int id) { return list.stream().anyMatch(o -> o.getRequest_id() == id); }
    public static boolean isVehicleCanBeAssigned(VehicleEntity vehicleEntity, RequestEntity requestEntity) { return (vehicleEntity.isAvailable() && requestEntity.getCargo_weight() > vehicleEntity.getCapacity()); }
}
