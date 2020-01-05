package com.rf.springsecurity.repository;


import com.rf.springsecurity.domain.cruises.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    Optional<Cruise> findByCruiseName(String cruiseName);
}
