package com.yourorganization.maven_sample.hw2.models;

public class Request {
    private final int requestId;
    private final String destination;
    private final String cargoType;
    private final int cargoQuantity;
    private final double cargoWeight;
    private int driver_id;
    private int vehicle_id;
    private double distance;

    public Request(int requestId, String destination, String cargoType, int cargoQuantity, double cargoWeight, int driverId, int vehicleId) {
        this.requestId = requestId;
        this.destination = destination;
        this.cargoType = cargoType;
        this.cargoQuantity = cargoQuantity;
        this.cargoWeight = cargoWeight;

        this.driver_id = driverId;
        this.vehicle_id = vehicleId;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getDestination() {
        return destination;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getCargoQuantity() {
        return cargoQuantity;
    }

    public double getCargoWeight() { return cargoWeight;}
    public void setDriverId(int value) { this.driver_id = value; }

    public void setVehicleId(int value) { this.vehicle_id = value; }

    public int getDriverId() { return driver_id; }

    public int getVehicleId() {
        return vehicle_id;
    }

    public double getDistance()
    {
        return distance;
    }
    public void setDistance(double value) { this.distance = value; }
}
