package com.yourorganization.maven_sample.hw2.models;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.yourorganization.maven_sample.hw2.exceptions.NoSuchRequestException;
import com.yourorganization.maven_sample.hw2.exceptions.RequestsGenerationException;
import com.yourorganization.maven_sample.hw2.exceptions.TripNotAssignedException;
import org.apache.log4j.Logger;

public class Driver {
    private static final Logger LOGGER = Logger.getLogger(Driver.class);

    private final int driverId;
    private String name;
    private final int experience;
    private boolean isAvailable;
    private double totalEarnings;

    public Driver(int driverId, String name, int experience) {
        this.driverId = driverId;
        this.setName(name);
        this.experience = experience;
        this.isAvailable = true;
        this.totalEarnings = 0.0;
    }
    public void vehicleBrokeDown(Vehicle vehicle)
    {

        requestRepair(vehicle);
    }
    public int getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void completeRequest(Trip trip) {

        try {
            int requestId = trip.getRequestId();
            if (Dispatcher.getInstance().getRequestById(requestId).getDriverId() != driverId) {
                throw new TripNotAssignedException("Request is not assigned to this driver.");
            }
            double tripPayment = calculateTripPayment(requestId);
            totalEarnings += tripPayment;
            setAvailable(true);

            if (trip.getTripStatus().equals("Broken")) {

                System.out.println("Driver " + getName() + " has not completed the request (Request ID: " + requestId +
                        "). Due to vehicle issues. Total earnings: $" + totalEarnings);

                LOGGER.info("Driver " + getName() + " has not completed the request (Request ID: " + requestId +
                        "). Due to vehicle issues. Total earnings: $" + totalEarnings);

                requestRepair(Dispatcher.getInstance().getVehicleById(trip.getRequest().getVehicleId()));

                return;
            }

            System.out.println("Driver " + getName() + " has completed the request (Request ID: " + requestId +
                    "). Total earnings: $" + totalEarnings);

            LOGGER.info("Driver " + getName() + " has completed the request (Request ID: " + requestId +
                    "). Total earnings: $" + totalEarnings);
        } catch (Exception e)
        {
            Dispatcher.logMessage("### "+e.getMessage());
        }
    }

    private double calculateTripPayment(int requestId) {
        try {
            return 100.0 + (experience * 100) + (Dispatcher.getInstance().getRequestById(requestId).getDistance());
        } catch (Exception e)
        {
            Dispatcher.logMessage("### "+e.getMessage());
        }
        return 0.0;
    }
    public void requestRepair(Vehicle vehicle) {



        try {
            Request request = Dispatcher.getInstance().getRequestByVehicleId(vehicle.getVehicleId());

            if (request == null)
                throw new NoSuchRequestException("Unable to find request by vehicle id: "+vehicle.getVehicleId());

            int requestId = request.getRequestId();
            LOGGER.info("Driver " + getName() + " has requested a vehicle repair.");


            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime now = Dispatcher.getInstance().getClock();

            Timestamp endDate = Timestamp.valueOf(now.format(dateFormat));

            String insertQuery = "INSERT INTO repair_requests (request_id, request_date) VALUES" +
                " (" +
                requestId + ", '" +
                endDate + "' )";

            DatabaseService.executeSQL(insertQuery);
            DatabaseService.close();


            vehicle.setAvailable(false);

        } catch (Exception e) {
            Dispatcher.logMessage(e.getMessage());
        }
    }
    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + getDriverId() +
                ", name='" + getName() + '\'' +
                ", experience=" + getExperience() +
                ", isAvailable=" + isAvailable +
                '}';
    }

    public void setAvailable(boolean b) {
        this.isAvailable = b;

        DatabaseService.executeSQL("UPDATE drivers SET available = "+ b+" WHERE driver_id = "+driverId);
    }
}
