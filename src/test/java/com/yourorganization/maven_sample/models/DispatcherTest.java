package com.yourorganization.maven_sample.models;

import com.yourorganization.maven_sample.TestMaven;
import com.yourorganization.maven_sample.services.DispatcherService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class DispatcherTest {

    private DispatcherService dispatcher;

    @Before
    public void setUp() {
        dispatcher = new DispatcherService();
    }

    @Test
    public void testProcessRequest() {

        ApplicationContext context =  SpringApplication.run(TestMaven.class, (String) null);
        dispatcher = context.getBean(DispatcherService.class, (Object) null);

    }

    @Test
    public void testGetVehicles() {

        ApplicationContext context =  SpringApplication.run(TestMaven.class, (String) null);

    }

}
