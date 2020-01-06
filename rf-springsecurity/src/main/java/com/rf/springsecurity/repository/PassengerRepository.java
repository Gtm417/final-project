package com.rf.springsecurity.repository;

import com.rf.springsecurity.domain.cruises.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

}
