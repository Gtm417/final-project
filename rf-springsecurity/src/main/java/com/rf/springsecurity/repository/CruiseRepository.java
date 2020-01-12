package com.rf.springsecurity.repository;


import com.rf.springsecurity.entity.cruises.Cruise;
import com.rf.springsecurity.entity.ports.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    Optional<Cruise> findByCruiseName(String cruiseName);
    //List<Excursion> findAllByCruiseId(long id);
}
