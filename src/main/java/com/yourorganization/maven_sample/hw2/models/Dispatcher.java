package com.yourorganization.maven_sample.hw2.models;

import com.yourorganization.maven_sample.hw2.exceptions.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class Dispatcher {

    private LocalDateTime clock = LocalDateTime.now();
    // Temporary arrays just for the generation method
    private HashMap<String, Integer> destinations = new HashMap<String, Integer>();
    private HashMap<String, Integer> cargoTypes = new HashMap<String, Integer>();

    private static Dispatcher instance;

    private List<Driver> availableDrivers = new ArrayList<>();
    private List<Vehicle> availableVehicles = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private List<Trip> newTrips = new ArrayList<>();

    public Dispatcher() {
        init();
    }
    private  void init()
    {
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

        this.availableDrivers = getDrivers();
        this.availableVehicles = getVehicles();
        this.requests = getRequests();
    }
    public void start()
    {
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
    private void startDay()
    {

        // Getting all available requests from database
        this.requests = this.getRequests();

        // Generating random requests for today
        List<Request> requestsForToday = this.gRandomRequests(0);

        // Processing new requests
        this.processRequests(requestsForToday);

        // Getting all available trips from database

        this.trips = getTrips();
        this.processTrips(this.trips);

        displayTrips();
    }
    public void closeDay()
    {
        clock = clock.plusDays(1);
    }
    public void displayTrips()
    {

        System.out.println("Current date: "+ clock.toLocalDate().toString());

        for (int i = 0; i < this.trips.size(); i++)
        {
            Trip trip = this.trips.get(i);
            System.out.println("------------------------------");
            System.out.println("Trip #"+trip.getTripId());
            System.out.println("Placed at "+trip.getStartDate());
            System.out.println("Ends at "+trip.getEndDate());
            System.out.println("Total payment:  "+trip.getTotalPayment());
            System.out.println("Status: "+trip.getTripStatus());
        }


    }

    // Simple generation method
    public List<Request> gRandomRequests(int size)
    {


        List<Request> newRequests = new ArrayList<>();
        Random random = new Random();

        if(size == 0)
            size = random.nextInt(5);

        int lastId = 0;
        if(this.requests.size() > 0)
            lastId = this.requests.get(this.requests.size()-1).getRequestId();

        for (int i = 0; i < size; i++, lastId++)
        {
            int id = lastId + 1;

            String destination = (String) destinations.keySet().toArray()[random.nextInt(destinations.size())];
            String cargoType = (String) cargoTypes.keySet().toArray()[random.nextInt(cargoTypes.size())];
            int cargoQuantity = random.nextInt(10);
            double cargoWeight = 100 + random.nextInt(2000);

            Request request = new Request(id, destination, cargoType, cargoQuantity, cargoWeight, 0, 0);
            newRequests.add(request);
        }

        return newRequests;
    }
    private void processTrips(List<Trip> trips)
    {
        for (int i = 0; i < trips.size(); i++)
        {
            processTrip(trips.get(i));
        }
    }
    private  void processTrip(Trip trip)
    {
        boolean result = trip.checkStatus();

        if(!result)
            return;

        if(trip.getTripStatus().equals("In Progress"))
        {
            int compareResult = trip.getEndDate().compareTo(Timestamp.valueOf(clock));
            if(compareResult <= 0)
            {
                trip.completeTrip("Completed");
            }
        }
    }
    private void processRequests(List<Request> requests)
    {
        newTrips.clear();
        try {

            // Building SQL Query
            String sql = "INSERT INTO requests (request_id, destination, cargo_type, cargo_quantity, driver_id, vehicle_id, cargo_weight) VALUES ";
            // SQL Query values
            String insertValues = "";


            // Processing requests
            for (int i = 0; i < requests.size(); i++)
                insertValues +=  renderRequest(requests.get(i));



            // If all requests was invalid we aren't going to declare it in database
            if (insertValues.isEmpty()) {

                System.out.println("---------------------------------");
                System.out.println("Nothing to declare in database.");

                return;
            }

            // Adding insert data to sql query
            sql += insertValues;
            // Correction of sql query
            sql = sql.substring(0, sql.length() - 1);

            // Executing
            DatabaseService.executeSQL(sql);
            DatabaseService.close();


            // Building another query to `trips` table
            String _sql = "INSERT INTO trips (trip_id, request_id, start_date, end_date, total_payment, status) VALUES ";


for (int i =0; i< this.newTrips.size(); i++) {
    Trip newTrip = this.newTrips.get(i);
    _sql += "(" + newTrip.getTripId() + ", "
            + newTrip.getRequestId() + ", '"
            + newTrip.getStartDate() + "', '"
            + newTrip.getEndDate() + "', "
            + newTrip.getTotalPayment() + ", '"
            + newTrip.getTripStatus()
            + "'),";
}
            _sql = _sql.substring(0, _sql.length() - 1);
            DatabaseService.executeSQL(_sql);


        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    private String renderRequest(Request request) {

        System.out.println("**************************************");
        System.out.println("Processing request number #"+request.getRequestId() + "...");

        try {

            System.out.println("Trip destination: " + request.getDestination());
            System.out.println("Cargo: ");
            System.out.println(
                    "Type: " + request.getCargoType() +
                    ", Quantity: " + request.getCargoQuantity()
            );

            System.out.println("Checking...");
            Random random = new Random();

            final int requiredExperience = cargoTypes.getOrDefault(request.getCargoType(), -1);

            if (requiredExperience == -1)
                throw new NoSuchCargoTypeException("Invalid cargo type. Change it, and try again.");

            System.out.println("Trying to find a vehicle...");
            Vehicle selectedVehicle = null;

            for (Vehicle vehicle : availableVehicles)
                if (vehicle.isAvailable() && vehicle.canBeAssignedToRequest(request))
                    if (selectedVehicle == null || vehicle.getCapacity() > selectedVehicle.getCapacity())
                        selectedVehicle = vehicle;

            if(selectedVehicle == null)
                throw new VehicleNotAvailableException("No suitable vehicle available for the request.");

            System.out.println("Vehicle found!");
            System.out.println(selectedVehicle.toString());


            System.out.println("Trying to find a driver...");

            Driver selectedDriver = availableDrivers.stream()
                    .filter(x -> x.isAvailable())
                    .filter(driver -> driver.getExperience() >= requiredExperience)
                    .findFirst()
                    .orElse(null);

            if(selectedDriver == null)
                throw new DriverNotAvailableException("No suitable driver available for the request.");

            System.out.println("Driver found!");
            System.out.println(selectedDriver.toString());

            request.setDriverId(selectedDriver.getDriverId());
            request.setVehicleId(selectedVehicle.getVehicleId());

            int destinationDistance = destinations.getOrDefault(request.getDestination(), -1);

            if(destinationDistance == -1)
                throw new TripNotAssignedException("Invalid destination.");

            request.setDistance(destinationDistance);

            Trip newTrip = new Trip(request.getRequestId());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            Timestamp endDate = Timestamp.valueOf(clock.plusDays(1+random.nextInt(5)).format(dateFormat));

            newTrip.setTotalPayment(100+random.nextInt(500));
            newTrip.setStartDate(Timestamp.valueOf(clock.format(dateFormat)));
            newTrip.setEndDate(endDate);
            newTrip.setDistance(destinationDistance);

            selectedVehicle.assignTrip(newTrip);

            this.trips.add(newTrip);
            this.newTrips.add(newTrip);

            // Setting vehicle, driver availability to false
            selectedVehicle.setAvailable(false);
            selectedDriver.setAvailable(false);


            this.requests.add(request);

            // Building SQL Query
            String sqlQuery = "";


            // Pushing to database
            sqlQuery += "(" +
                    request.getRequestId() + ", '" +
                    request.getDestination() + "', '" +
                    request.getCargoType() + "', " +
                    request.getCargoQuantity() + ", " +
                    request.getDriverId() + ", " +
                    request.getVehicleId() + ", " +
                    request.getCargoWeight() + "),";


            return sqlQuery;




        } catch (Exception e) {

            System.out.println("Request cannot be processed due to: " + e.getMessage());
            return "";
        }

    }
    public void completeTrip(Trip trip)
    {
        System.out.println(trip.getTripStatus());
        DatabaseService.executeSQL("UPDATE trips SET status = '"+trip.getTripStatus()+"' WHERE trip_id = "+trip.getTripId());
        DatabaseService.close();

        Driver driver = getDriverById(trip.getRequest().getDriverId());
        Vehicle vehicle = getVehicleById(trip.getRequest().getVehicleId());


        try {
            driver.completeRequest(trip);
            vehicle.completeTrip(trip);
        } catch (TripNotAssignedException e)
        {
            System.err.println(e.getMessage());
        }
    }
    private List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/test-db", "sa", "admin");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM drivers");

            while (resultSet.next()) {
                int driverId = resultSet.getInt("driver_id");
                String name = resultSet.getString("name");
                int experience = resultSet.getInt("experience");

                Driver driver = new Driver(driverId, name, experience);
                drivers.add(driver);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers;
    }

    private List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/test-db", "sa", "admin");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicles");

            while (resultSet.next()) {
                int vehicleId = resultSet.getInt("vehicle_id");
                int capacity = resultSet.getInt("capacity");
                boolean isAvailable = resultSet.getBoolean("available");

                Vehicle vehicle = new Vehicle(vehicleId, capacity, isAvailable);
                vehicles.add(vehicle);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }


    public Driver getDriverById(int id)
    {
        for (Driver driver : availableDrivers) {
            if (driver.getDriverId() == id) {
                return driver;
            }
        }
        return null;
    }
    public Vehicle getVehicleById(int id)
    {
        for (Vehicle vehicle : availableVehicles) {
            if (vehicle.getVehicleId() == id) {
                return vehicle;
            }
        }
        return null;
    }
public Request getRequestById(int id)
    {
        for (Request request : requests) {
            if (request.getRequestId() == id) {
                return request;
            }
        }
        return null;
    }
    private List<Trip> getTrips() {
        List<Trip> requests = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/test-db", "sa", "admin");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM trips");

            while (resultSet.next()) {
                int trip_id = resultSet.getInt("trip_id");
                int request_id = resultSet.getInt("request_id");
                Timestamp start_date = resultSet.getTimestamp("start_date");
                Timestamp end_date = resultSet.getTimestamp("end_date");
                double total_payment = resultSet.getDouble("total_payment");
                String status = resultSet.getString("status");

                Trip request = new Trip(trip_id, request_id, start_date, end_date, total_payment, status);


                int destinationDistance = destinations.getOrDefault(getRequestById(request_id).getDestination(), -1);

                if(destinationDistance == -1)
                    throw new TripNotAssignedException("Invalid destination.");

                request.setDistance(destinationDistance);
                requests.add(request);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }
    private List<Request> getRequests() {
        List<Request> requests = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/test-db", "sa", "admin");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM requests");

            while (resultSet.next()) {
                int requestId = resultSet.getInt("request_id");
                String destination = resultSet.getString("destination");
                String cargo_type = resultSet.getString("cargo_type");
                int cargo_quantity = resultSet.getInt("cargo_quantity");
                double cargo_weight = resultSet.getDouble("cargo_weight");
                int driver_id = resultSet.getInt("driver_id");
                int vehicle_id = resultSet.getInt("vehicle_id");

                Request request = new Request(requestId, destination, cargo_type, cargo_quantity, cargo_weight, driver_id, vehicle_id);
                requests.add(request);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }
    public void startTrip(Trip trip)
    {
        System.out.println("Trip started #"+trip.getTripId());

        getVehicleById(getRequestById(trip.getRequestId()).getVehicleId()).startDriving(trip);
    }
    public Request getRequestByVehicleId(int vehicleId) throws NoSuchRequestException
    {
        Request request = null;
        try {
            request = this.requests.stream().filter(x -> x.getVehicleId() == vehicleId).findFirst().orElse(null);

            if(request == null)
                throw new NoSuchRequestException("No requests was found.");
        } catch (NoSuchRequestException e)
        {
            System.err.println(e.getMessage());
        }

        return request;
    }
    public LocalDateTime getClock()
    {
        return clock;
    }
    public void startTripsAsync(List<Trip> trips) {
        for (Trip trip : trips) {
            Thread tripThread = new Thread(() -> {
                startTrip(trip);
            });
            tripThread.start();
        }
    }
    public static int generateUniquePositiveNumber() {
        Instant now = Instant.now();
        long timestamp = now.toEpochMilli(); // Get the current timestamp in milliseconds
        return (int) (timestamp & 0x7FFFFFFF); // Use the lower 31 bits to ensure a positive number
    }
}

