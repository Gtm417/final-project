package com.rf.springsecurity;

import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnhandledCruiseName;
import com.rf.springsecurity.services.BuyCruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
