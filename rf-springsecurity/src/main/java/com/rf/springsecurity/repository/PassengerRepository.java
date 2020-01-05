package com.rf.springsecurity.repository;

import com.rf.springsecurity.domain.cruises.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {

}
