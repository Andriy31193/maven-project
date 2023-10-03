package com.yourorganization.maven_sample.hw2.models;

import org.apache.log4j.Logger;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Trip {


    private static final Logger LOGGER = Logger.getLogger(Trip.class);

    private int tripId;
    private int requestId;
    private double distance;
    private Timestamp startDate;
    private Timestamp endDate;
    private double totalPayment;
    private String tripStatus;

    private double currentDistance = 0.0;

    public Trip(int tripId, int requestId, Timestamp startDate, Timestamp endDate) {
        init(tripId,requestId,startDate, endDate, 0.0, "In Progress");
    }
    public Trip(int tripId, int requestId, Timestamp startDate, Timestamp endDate, double totalPayment, String status) {
        init(tripId,requestId,startDate, endDate, totalPayment, status);
    }

    private void init(int tripId, int requestId, Timestamp startDate, Timestamp endDate, double totalPayment, String status)
    {
        this.tripId = tripId;
        this.requestId = requestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayment = totalPayment;
        this.tripStatus = status;

        System.out.println("New trip placed: "+ this.toString());
    }
    public Trip(int requestId) {
        LocalDateTime requestDate = LocalDateTime.now();

        this.requestId = requestId;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        Timestamp sDate = Timestamp.valueOf(requestDate.format(dateFormat));

        Random random = new Random();

        init(requestId+requestDate.getSecond()+random.nextInt(1000), requestId, sDate, sDate, 0, "In Progress");
    }
    public double getCargoWeight() { return 0; }
    public int getTripId() {
        return tripId;
    }
    public Request getRequest() { return Dispatcher.getInstance().getRequestById(requestId); }

    public int getRequestId() {
        return requestId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }
    public void setStartDate(Timestamp endDate) {
        this.startDate = endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    public boolean checkStatus() {

        try {
            Random random = new Random();

            int randValue = random.nextInt(10);

            // Emulation of car broke down situation
            if (randValue <= 1) {

                String errorMessage = "Trip cannot be completed due to vehicle issues.";
                LOGGER.info(errorMessage);
                System.out.println(errorMessage);
                this.completeTrip("Broken");



                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
    public Driver getCurrentDriver()
    {
        return Dispatcher.getInstance().getDriverById(getRequest().getDriverId());
    }
    public double getDistance() { return this.distance; }
    public void setDistance(int value) { distance = value; }
    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    @Override
    public String toString() {
        return " Trip [" +
                "tripId=" + tripId +
                ", requestId=" + requestId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPayment=" + totalPayment +
                ", tripStatus='" + tripStatus + '\''+
                ']';
    }

    public double calculateTripDuration() {
        if (endDate != null) {
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            long durationMillis = endTime - startTime;
            return durationMillis / (1000.0 * 60 * 60);
        } else {
            // Handle the case where the trip hasn't ended yet
            return 0;
        }
    }

    public void completeTrip(String status) {

        this.totalPayment += calculateTripDuration();
        //this.endDate = new Date();
        this.tripStatus = status;

        Dispatcher.getInstance().completeTrip(this);

    }
}

