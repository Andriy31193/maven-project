package com.yourorganization.maven_sample.hw2.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DispatcherTest {

    private Dispatcher dispatcher;

    @Before
    public void setUp() {
        dispatcher = new Dispatcher();
    }

    @Test
    public void testProcessRequest() {

        ArrayList<Request> requests = new ArrayList<>();

        try {
            Request request = new Request(999, "New York City", "Electronics", 3, 10.0, 0,0);
            Request request1 = new Request(1000, "Boston", "Electronics2", 3, 100.0, 1,99999);

            requests.add(request);
            requests.add(request1);
            dispatcher.processRequests(requests);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetVehicles() {

        List<Vehicle> vehicles;

        try {

            vehicles = dispatcher.getVehicles();

            for (Vehicle vehicle : vehicles)
                System.out.println(vehicle.toString());

        } catch (Exception e) {
System.err.println(e.getMessage());
        }
    }

}
