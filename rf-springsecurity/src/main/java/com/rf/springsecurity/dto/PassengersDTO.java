package com.rf.springsecurity.dto;

import com.rf.springsecurity.entity.cruises.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengersDTO {
    private List<Passenger> passengers;
}