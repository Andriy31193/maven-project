package com.yourorganization.maven_sample.services;

import com.yourorganization.maven_sample.entity.*;
import com.yourorganization.maven_sample.exceptions.*;
import com.yourorganization.maven_sample.services.interfaces.IDispatcherService;
import com.yourorganization.maven_sample.utils.dispatcher.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class DispatcherService implements IDispatcherService {

    private static final Logger LOGGER = Logger.getLogger(DispatcherService.class);


    private LocalDateTime clock = LocalDateTime.now();

    /// SERVICES ///
    private DriverService driverService = null;
    private VehicleService vehicleService = null;
    private TripService tripService = null;
    private RequestService requestService = null;
    private RepairService repairService = null;

    @Override
    public void init(DriverService dS, VehicleService vS, TripService tS, RequestService rS, RepairService riS) {

        this.driverService = dS;
        this.vehicleService = vS;
        this.tripService = tS;
        this.requestService = rS;
        this.repairService = riS;

        destinations.put("New York City", 250);
        destinations.put("Los Angeles", 400);
        destinations.put("Chicago", 300);
        destinations.put("San Francisco", 450);
        destinations.put("Miami", 320);
        destinations.put("Seattle", 350);
        destinations.put("Las Vegas", 280);
        destinations.put("Boston", 220);
        destinations.put("Houston", 380);
        destinations.put("Atlanta", 270);


        cargoTypes.put("Electronics", 3);
        cargoTypes.put("Furniture", 5);
        cargoTypes.put("Clothing", 2);
        cargoTypes.put("Food", 4);
        cargoTypes.put("Machinery", 8);
        cargoTypes.put("Chemicals", 6);
        cargoTypes.put("Books", 2);
        cargoTypes.put("Toys", 1);
        cargoTypes.put("Jewelry", 7);
        cargoTypes.put("Medical Supplies", 5);

        updateLists();

        startProgram();
    }


    @Override
    public void clearLists()
    {
        tripEntities.clear();
        availableDriverEntities.clear();
        availableVehicleEntities.clear();
        requestEntities.clear();
    }
    @Override
    public void updateLists()
    {
        clearLists();

        tripEntities.addAll(tripService.findAll());
        requestEntities.addAll(requestService.findAll());
        availableDriverEntities.addAll(driverService.findAll());
        availableVehicleEntities.addAll(vehicleService.findAll());
    }

    /// MAIN METHODS ///
    @Override
    public void startProgram() {
        startDay();
        Scanner scanner = new Scanner(System.in);
        while (true)
        {

            System.out.println("Start new day? 1/0 ");

            System.out.println("Your choice: ");

            int selectedOption = 0;

            if(scanner.hasNextInt())
                selectedOption = scanner.nextInt();

            System.out.println(selectedOption);

            if(selectedOption == 1)
            {
                closeDay();
                startDay();
            }
            else break;
        }
        scanner.close();
    }
    @Override
    public void startDay()
    {
        //logMessage("New day started: "+clock.toLocalDate().toString());
        try {

            updateLists();

            // Generating random requests for today
            List<RequestEntity> requestsForToday = DispatcherUtils.gRandomRequests(requestEntities,destinations, cargoTypes, 0);

            logMessage("Dispatcher got "+requestsForToday.size()+" requests today!");

            // Processing new requests
            newTripEntities.clear();

            for (RequestEntity requestEntity : requestEntities)
                processRequest(requestEntity);


            // Getting all available trips from database

            updateLists();

            this.processTrips(this.tripEntities);

            displayTrips();
        } catch (Exception e)
        {
            logMessage("### Unable to start the day due to: "+ e.getMessage());
        }
    }
    public void closeDay() { clock = clock.plusDays(1); }

    private void processRequest(RequestEntity requestEntity) {

        logMessage("**************************************");
        logMessage("Processing request number #"+ requestEntity.getRequest_id() + "...");

        if(DispatcherUtils.isRequestExists(this.requestEntities, requestEntity.getRequest_id()))
        {
            logMessage("Request number #"+ requestEntity.getRequest_id()+" is already proceeded");
            return;
        }

        try {

            System.out.println("Trip destination: " + requestEntity.getDestination());
            System.out.println("Cargo: ");
            System.out.println(
                    "Type: " + requestEntity.getCargo_type() +
                            ", Quantity: " + requestEntity.getCargo_quantity()
            );

            System.out.println("Checking...");
            Random random = new Random();

            final int requiredExperience = cargoTypes.getOrDefault(requestEntity.getCargo_type(), -1);

            if (requiredExperience == -1)
                throw new NoSuchCargoTypeException("Invalid cargo type. Change it, and try again.");

            System.out.println("Trying to find a vehicle...");
            VehicleEntity selectedVehicleEntity = null;

            for (VehicleEntity vehicleEntity : availableVehicleEntities)
                if (vehicleEntity.isAvailable() && DispatcherUtils.isVehicleCanBeAssigned(vehicleEntity, requestEntity))
                    if (selectedVehicleEntity == null || vehicleEntity.getCapacity() > selectedVehicleEntity.getCapacity())
                        selectedVehicleEntity = vehicleEntity;

            if(selectedVehicleEntity == null)
                throw new VehicleNotAvailableException("No suitable vehicle available for the request.");


            System.out.println("Vehicle found!");
            System.out.println(selectedVehicleEntity.toString());


            System.out.println("Trying to find a driver...");

            DriverEntity selectedDriverEntity = DispatcherUtils.findDriverWithExperience(availableDriverEntities, requiredExperience);

            if(selectedDriverEntity == null)
                throw new DriverNotAvailableException("No suitable driver available for the request.");

            System.out.println("Driver found!");
            System.out.println(selectedDriverEntity.toString());

            requestEntity.setDriver_id(selectedDriverEntity);
            requestEntity.setVehicle_id(selectedVehicleEntity);

            int destinationDistance = destinations.getOrDefault(requestEntity.getDestination(), -1);

            if(destinationDistance == -1)
                throw new TripNotAssignedException("Invalid destination.");

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp endDate = Timestamp.valueOf(clock.plusDays(1+random.nextInt(5)).format(dateFormat));

            TripEntity newTripEntity = new TripEntity();
            newTripEntity.setRequest_id(requestEntity);
            newTripEntity.setStatus("In Progress");
            newTripEntity.setStart_date(Timestamp.valueOf(clock.format(dateFormat)));
            newTripEntity.setEnd_date(endDate);
            newTripEntity.setTotal_payment((double)(100 + random.nextInt(500)));

            logMessage("New trip created: "+ newTripEntity.toString());

            this.tripEntities.add(newTripEntity);

            tripService.customInsert(newTripEntity);

            this.newTripEntities.add(newTripEntity);

            // Setting vehicle, driver availability to false
            vehicleService.setAvailability(selectedVehicleEntity, false);
            driverService.setAvailability(selectedDriverEntity, false);



            this.requestEntities.add(requestEntity);


            requestService.customInsert(requestEntity.getDestination(),
                    requestEntity.getCargo_type(),
                    requestEntity.getCargo_quantity(),
                    requestEntity.getDriver_id().getDriver_id(),
                    requestEntity.getVehicle_id().getVehicle_id(),
                    requestEntity.getCargo_weight());

        } catch (Exception e) {

            System.out.println("Request cannot be processed due to: " + e.getMessage());
        }

    }


    private void processTrips(List<TripEntity> tripEntities)
    {
        for (TripEntity tripEntity : tripEntities)
            processTrip(tripEntity);
    }
    private void processTrip(TripEntity tripEntity)
    {
        if(tripEntity == null)
            return;

        DispatcherUtils.processTripStatus(vehicleService.findById(tripEntity.getRequest_id().getVehicle_id().getVehicle_id()), tripEntity);


        if(tripEntity.getStatus().equals("In Progress"))
        {
            int compareResult = tripEntity.getEnd_date().compareTo(Timestamp.valueOf(clock));
            if(compareResult <= 0)
            {
                completeTrip(tripEntity);
            }
        }

        tripService.updateStatus(tripEntity, tripEntity.getStatus());
    }
    @Override
    public void completeTrip(TripEntity tripEntity) {
        RequestEntity requestEntity = tripEntity.getRequest_id();
        DriverEntity driverEntity = requestEntity.getDriver_id();

        if (tripEntity.getStatus().equals("Issues")) {

            DispatcherUtils.requestVehicleRepair(repairService, clock, requestEntity.getRequest_id());
            LOGGER.info("Driver " + driverEntity.getName() + " has not completed the request (Request ID: " + requestEntity.getRequest_id() + "). Due to vehicle issues.");
        }

        tripService.completeTrip(tripEntity);
        driverService.completeTrip(tripEntity);
        vehicleService.completeTrip(tripEntity);

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /// LOGGING ///
    public void displayTrips()
    {
        try {
            logMessage("------------------------------");
            logMessage("All trips:");
            logMessage("------------------------------");

            for (TripEntity tripEntity : tripEntities) {

                if (tripEntity == null)
                    throw new TripNotAssignedException("### Cannot display trip.");


                logMessage("------------------------------");
                logMessage("Trip #" + tripEntity.getTrip_id());
                logMessage("Placed at " + tripEntity.getStart_date());
                logMessage("Ends at " + tripEntity.getEnd_date());
                logMessage("Total payment:  " + tripEntity.getTotal_payment());
                logMessage("Status: " + tripEntity.getStatus());
            }
        } catch (TripNotAssignedException e)
        {
            logMessage(e.getMessage());
        }

    }
    public static void logMessage(String message)
    {
        LOGGER.info(message);
        System.out.println(message);
    }

}
