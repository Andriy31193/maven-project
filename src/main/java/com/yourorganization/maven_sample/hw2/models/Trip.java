package com.yourorganization.maven_sample.hw2.models;

import com.yourorganization.maven_sample.hw2.exceptions.DriverNotAvailableException;
import com.yourorganization.maven_sample.hw2.exceptions.NoSuchRequestException;
import org.apache.log4j.Logger;
import org.junit.platform.commons.function.Try;

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
    public Trip(int requestId) {
        LocalDateTime requestDate = LocalDateTime.now();

        this.requestId = requestId;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        Timestamp sDate = Timestamp.valueOf(requestDate.format(dateFormat));

        Random random = new Random();

        init(requestId+requestDate.getSecond()+random.nextInt(1000), requestId, sDate, sDate, 0, "In Progress");
    }

    private void init(int tripId, int requestId, Timestamp startDate, Timestamp endDate, double totalPayment, String status)
    {
        this.tripId = tripId;
        this.requestId = requestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayment = totalPayment;
        this.tripStatus = status;

        Dispatcher.logMessage("Trip #" + tripId + this.toString());
    }

    public double calculateTripDuration() {
        if (endDate != null) {
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            long durationMillis = endTime - startTime;
            return durationMillis / (1000.0 * 60 * 60);
        } else return 0;

    }

    public void completeTrip(String status) {

        this.totalPayment += calculateTripDuration();
        //this.endDate = new Date();
        this.tripStatus = status;

        Dispatcher.getInstance().completeTrip(this);

    }

    public boolean checkStatus() {

        try {

            if(getTripStatus().equals("Broken")) {
                return false;
            }

            Random random = new Random();

            int randValue = random.nextInt(10);

            // Emulation of car broke down situation
            if (randValue <= 1) {

                Dispatcher.logMessage("Trip cannot be completed due to vehicle issues.");
                this.completeTrip("Broken");



                return false;
            }

            return true;
        } catch (Exception e) {
            Dispatcher.logMessage(e.getMessage());
        }

        return false;
    }

    /// SETTERS ///

    public void setDistance(int value) { distance = value; }
    public void setTripStatus(String tripStatus) { this.tripStatus = tripStatus; }
    public void setTotalPayment(double totalPayment) { this.totalPayment = totalPayment; }

    /// GETTERS ///

    public Driver getCurrentDriver() {
        try {
            Dispatcher.getInstance().getDriverById(getRequest().getDriverId());

            return Dispatcher.getInstance().getDriverById(getRequest().getDriverId());
        } catch (Exception e)
        {
            Dispatcher.logMessage(e.getMessage());

            return null;
        }
    }
    public Request getRequest() {
        try {
            return Dispatcher.getInstance().getRequestById(requestId);
        } catch (NoSuchRequestException e)
        {
            Dispatcher.logMessage("### " + e.getMessage());

            return null;
        }
    }
    public double getTotalPayment() { return totalPayment; }
    public int getRequestId() { return requestId; }
    public Timestamp getStartDate() { return startDate; }
    public double getDistance() { return this.distance; }
    public String getTripStatus() { return tripStatus; }
    public Timestamp getEndDate() { return endDate; }
    public void setStartDate(Timestamp endDate) { this.startDate = endDate; }
    public void setEndDate(Timestamp endDate) { this.endDate = endDate; }
    public double getCargoWeight() { return 0; }
    public int getTripId() { return tripId; }


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
}

