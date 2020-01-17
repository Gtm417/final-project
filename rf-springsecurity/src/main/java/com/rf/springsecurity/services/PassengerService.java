package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.cruise.Passenger;
import com.rf.springsecurity.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    public void saveNewPassenger(Passenger passenger){
        passengerRepository.save(passenger);
    }
}
