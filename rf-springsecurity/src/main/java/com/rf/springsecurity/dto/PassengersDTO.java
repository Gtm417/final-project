package com.rf.springsecurity.dto;

import com.rf.springsecurity.domain.cruises.Passenger;
import lombok.Data;

import java.util.List;

@Data
public class PassengersDTO {
    private List<Passenger> passengers;
}
