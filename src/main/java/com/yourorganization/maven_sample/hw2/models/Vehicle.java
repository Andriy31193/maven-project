package com.yourorganization.maven_sample.hw2.models;

import com.yourorganization.maven_sample.hw2.exceptions.OverCapacityException;
import com.yourorganization.maven_sample.hw2.exceptions.TripNotAssignedException;
import com.yourorganization.maven_sample.hw2.exceptions.VehicleNotAvailableException;
import org.apache.log4j.Logger;

import java.util.Random;

public class Vehicle {

    private static double rangeMax = 50, rangeMin = 10;

    private static final Logger LOGGER = Logger.getLogger(Vehicle.class);

    private int vehicleId;
    private int capacity;

    private double currentDistance;
    private Trip trip;
    private boolean isAvailable;

    public Vehicle(int vehicleId, int capacity, boolean available) {
        this.vehicleId = vehicleId;
        this.capacity = capacity;
        this.isAvailable = available;
        LOGGER.info("123");
    }

    public void startDriving(Trip trip)
    {
        double distance = trip.getDistance();
        Random random = new Random();

        try {
            while (currentDistance < distance) {

                double randomSpeed = rangeMin + (rangeMax - rangeMin) * random.nextDouble();

                // Emulating case that car has some issues and needs repair
                if(randomSpeed >= (rangeMax - 1))
                    break;

                currentDistance += randomSpeed;

                if(currentDistance > distance)
                    currentDistance = distance;




                Thread.sleep(500);

                System.out.println("#"+vehicleId+" Current distance: " + round(currentDistance, 2)+"/"+distance);
            }

            if(currentDistance >= distance) {
                Dispatcher.getInstance().completeTrip(trip);
            } else {
                try {
                    trip.getCurrentDriver().vehicleBrokeDown(this);
                } catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }




        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public int getVehicleId() {
        return vehicleId;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public boolean canBeAssignedToRequest(Request request) {
        return request.getCargoWeight() <= this.capacity;
    }
    public void assignTrip(Trip trip) throws VehicleNotAvailableException, OverCapacityException {
        if (!isAvailable) {
            throw new VehicleNotAvailableException("Vehicle " + vehicleId + " is not available for a new trip.");
        }

        if (trip.getCargoWeight() > capacity) {
            throw new OverCapacityException("Cargo weight exceeds the vehicle's capacity.");
        }

        this.trip = trip;
        setAvailable(false);
        LOGGER.info("Vehicle " + vehicleId + " has been assigned a new trip (Trip ID: " + trip.getTripId() + ").");
    }

    public void setAvailable(boolean b)
    {
        isAvailable = b;

        DatabaseService.executeSQL("UPDATE vehicles SET available = "+ b +" WHERE vehicle_id = "+vehicleId);
        DatabaseService.close();
    }

    public void completeTrip(Trip trip) throws TripNotAssignedException {
        if (trip.getRequest().getVehicleId() != vehicleId) {
            throw new TripNotAssignedException("Trip is not assigned to this vehicle.");
        }

        setAvailable(true);
        LOGGER.info("Vehicle " + vehicleId + " has completed the trip (Trip ID: " + trip.getTripId() + ").");
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", capacity=" + capacity +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
