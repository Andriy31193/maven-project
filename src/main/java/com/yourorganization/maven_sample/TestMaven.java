package com.yourorganization.maven_sample;

import com.yourorganization.maven_sample.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class TestMaven {
    public static void main(String[] args) {

        ApplicationContext context =  SpringApplication.run(TestMaven.class, args);

        DriverService driverService = context.getBean(DriverService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        TripService tripService = context.getBean(TripService.class);
        RequestService requestService = context.getBean(RequestService.class);
        RepairService repairService = context.getBean(RepairService.class);

        DispatcherService dispatcherService = context.getBean(DispatcherService.class);
        dispatcherService.init(driverService, vehicleService, tripService, requestService, repairService);

    }



}
