package com.yourorganization.maven_sample.hw2.models;

import com.yourorganization.maven_sample.hw2.exceptions.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Dispatcher {

    private static final Logger LOGGER = Logger.getLogger(Dispatcher.class);

    private LocalDateTime clock = LocalDateTime.now();
    // Temporary arrays just for the generation method
    private final HashMap<String, Integer> destinations = new HashMap<>();
    private final HashMap<String, Integer> cargoTypes = new HashMap<>();

    private static Dispatcher instance;

    private List<Driver> availableDrivers = new ArrayList<>();
    private List<Vehicle> availableVehicles = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private final List<Trip> newTrips = new ArrayList<>();


    /// INITIALIZATION ///
    public static Dispatcher getInstance() {
        if (instance == null)
            instance = new Dispatcher();

        return instance;
    }
    public Dispatcher() {
        init();
    }
    private  void init()
    {
        if (instance == null)
            instance = this;

        try {

            this.requests = this.getRequests();
            this.availableDrivers = this.getDrivers();
            this.availableVehicles = this.getVehicles();

        } catch (Exception e)
        {
            logMessage("### "+e.getMessage());
        }

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
    }


    /// MAIN METHODS ///
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
        logMessage("New day started: "+clock.toLocalDate().toString());
        // Getting all available requests from database
        try {

            this.requests = this.getRequests();
            this.availableDrivers = this.getDrivers();
            this.availableVehicles = this.getVehicles();

            // Generating random requests for today
            List<Request> requestsForToday = this.gRandomRequests(0);

            logMessage("Dispatcher got "+requestsForToday.size()+" requests today!");

            // Processing new requests
            this.processRequests(requestsForToday);

            // Getting all available trips from database

            this.trips = getTrips();
            this.processTrips(this.trips);

            displayTrips();
        } catch (Exception e)
        {
            logMessage("### Unable to start the day due to: "+ e.getMessage());
        }
    }
    public void closeDay()
    {
        clock = clock.plusDays(1);
    }

    // Simple generation method
    public List<Request> gRandomRequests(int size)
    {
        List<Request> newRequests = new ArrayList<>();

        try {
            Random random = new Random();

            if (size == 0)
                size = random.nextInt(5);

            int lastId = 0;
            if (this.requests.size() > 0)
                lastId = this.requests.get(this.requests.size() - 1).getRequestId();

            if(this.destinations.isEmpty() || this.cargoTypes.isEmpty())
                throw new RequestsGenerationException("### Destinations or cargo types array is empty. Fill arrays with correct data and try again.");

            for (int i = 0; i < size; i++, lastId++) {
                int id = lastId + 1;

                String destination = (String) destinations.keySet().toArray()[random.nextInt(destinations.size())];
                String cargoType = (String) cargoTypes.keySet().toArray()[random.nextInt(cargoTypes.size())];
                int cargoQuantity = random.nextInt(10);
                double cargoWeight = 100 + random.nextInt(2000);

                Request request = new Request(id, destination, cargoType, cargoQuantity, cargoWeight, 0, 0);
                newRequests.add(request);

            }

            return newRequests;

        } catch (RequestsGenerationException e)
        {
            logMessage(e.getMessage());
        }

        return newRequests;
    }
    // Methods for processing new daily requests //
    public void processRequests(List<Request> requests) throws Exception {
        newTrips.clear();

        // Building SQL Query
        String sql = "INSERT INTO requests (request_id, destination, cargo_type, cargo_quantity, driver_id, vehicle_id, cargo_weight) VALUES ";
        // SQL Query values
        StringBuilder insertValues = new StringBuilder();


        // Processing requests
        for (Request request : requests) insertValues.append(processRequest(request));


        // If all requests was invalid we aren't going to declare it in database
        if (insertValues.length() == 0) {

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


        // Building another query to `trips` table
        StringBuilder _sql = new StringBuilder("INSERT INTO trips (trip_id, request_id, start_date, end_date, total_payment, status) VALUES ");


        for (Trip newTrip : this.newTrips) {
            if (newTrip == null)
                throw new Exception("Trip element is empty. Please check database and try again.");

            _sql.append("(").append(newTrip.getTripId()).append(", ").append(newTrip.getRequestId()).append(", '").append(newTrip.getStartDate()).append("', '").append(newTrip.getEndDate()).append("', ").append(newTrip.getTotalPayment()).append(", '").append(newTrip.getTripStatus()).append("'),");
        }
        _sql = new StringBuilder(_sql.substring(0, _sql.length() - 1));

        DatabaseService.executeSQL(_sql.toString());
    }
    private String processRequest(Request request) {

        logMessage("**************************************");
        logMessage("Processing request number #"+request.getRequestId() + "...");

        if(isRequestExists(this.requests, request.getRequestId()))
        {
            logMessage("Request number #"+request.getRequestId()+" is already proceeded");
            return "";
        }

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
                    .filter(Driver::isAvailable)
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

            logMessage("New trip created: "+ newTrip.toString());

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
    /// METHODS FOR PROCESSING TRIPS ///
    private void processTrips(List<Trip> trips)
    {
        for (Trip trip : trips) processTrip(trip);
    }
    private  void processTrip(Trip trip)
    {
        if(trip == null)
            return;

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

    /// METHOD TO COMPLETE THE TRIP ///
    public void completeTrip(Trip trip)
    {
        try {

            DatabaseService.executeSQL("UPDATE trips SET status = '"+trip.getTripStatus()+"' WHERE trip_id = "+trip.getTripId());
            DatabaseService.close();

            Driver driver = getDriverById(trip.getRequest().getDriverId());
            Vehicle vehicle = getVehicleById(trip.getRequest().getVehicleId());



            driver.completeRequest(trip);
            vehicle.completeTrip(trip);
        } catch (Exception e)
        {
            logMessage(e.getMessage());
        }
    }

    /// GETTERS ///

    public Driver getDriverById(int id) throws DriverNotAvailableException
    {
        for (Driver driver : availableDrivers)
            if (driver.getDriverId() == id)
                return driver;

        throw new DriverNotAvailableException("Unable to find a driver with id: " + id);
    }
    public Vehicle getVehicleById(int id) throws VehicleNotAvailableException
    {
        for (Vehicle vehicle : availableVehicles)
            if (vehicle.getVehicleId() == id)
                return vehicle;

        throw new VehicleNotAvailableException("Unable to find a vehicle with id: " + id);
    }
    public Request getRequestById(int id) throws NoSuchRequestException
    {
        for (Request request : requests)
            if (request.getRequestId() == id)
                return request;

        throw new NoSuchRequestException("Unable to find requests with id: "+id);
    }
    public Request getRequestByVehicleId(int vehicleId) throws NoSuchRequestException {
        Request request = this.requests.stream().filter(x -> x.getVehicleId() == vehicleId).findFirst().orElse(null);

        if (request == null)
            throw new NoSuchRequestException("Unable to find request that has vehicle id equals: " + vehicleId);

        return request;
    }
    public boolean isRequestExists(final List<Request> list, final int id){
        return list.stream().anyMatch(o -> o.getRequestId() == id);
    }
    public boolean isTripExists(final List<Trip> list, final int id){
        return list.stream().anyMatch(o -> o.getTripId() == id);
    }
    public LocalDateTime getClock() { return clock; }

    /// GETTING DATA FROM DATABASE ///

    private List<Request> getRequests() throws SQLException {
        List<Request> requests = new ArrayList<>();

        ResultSet resultSet = DatabaseService.executeQuerySQL("SELECT * FROM requests");

        if(resultSet == null)
            throw new SQLException("Unable to get result test in getRequests().");

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

        return requests;
    }

    private List<Trip> getTrips() throws Exception {
        List<Trip> requests = new ArrayList<>();

        ResultSet resultSet = DatabaseService.executeQuerySQL("SELECT * FROM trips");

        if(resultSet == null)
            throw new SQLException("Unable to get result test in getTrips().");

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

        return requests;
    }

    public List<Vehicle> getVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();


        ResultSet resultSet = DatabaseService.executeQuerySQL("SELECT * FROM vehicles");


        if (resultSet == null)
            throw new SQLException("Unable to get result test in getTrips().");

        while (resultSet.next()) {
            int vehicleId = resultSet.getInt("vehicle_id");
            int capacity = resultSet.getInt("capacity");
            boolean isAvailable = resultSet.getBoolean("available");

            Vehicle vehicle = new Vehicle(vehicleId, capacity, isAvailable);
            vehicles.add(vehicle);
        }

        resultSet.close();

        return vehicles;
    }

    private List<Driver> getDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();

        ResultSet resultSet = DatabaseService.executeQuerySQL("SELECT * FROM drivers");


        if (resultSet == null)
            throw new SQLException("Unable to get result test in geDrivers().");

        while (resultSet.next()) {
            int driverId = resultSet.getInt("driver_id");
            String name = resultSet.getString("name");
            int experience = resultSet.getInt("experience");

            Driver driver = new Driver(driverId, name, experience);
            drivers.add(driver);
        }

        resultSet.close();


        return drivers;
    }

    /// LOGGING ///
    public void displayTrips()
    {
        try {
            logMessage("------------------------------");
            logMessage("All trips:");
            logMessage("------------------------------");

            for (Trip trip : this.trips) {

                if (trip == null)
                    throw new TripNotAssignedException("### Cannot display trip.");


                logMessage("------------------------------");
                logMessage("Trip #" + trip.getTripId());
                logMessage("Placed at " + trip.getStartDate());
                logMessage("Ends at " + trip.getEndDate());
                logMessage("Total payment:  " + trip.getTotalPayment());
                logMessage("Status: " + trip.getTripStatus());
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

