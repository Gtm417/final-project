package com.rf.springsecurity.repository;


import com.rf.springsecurity.entity.cruise.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    Optional<Cruise> findByCruiseName(String cruiseName);
    //List<Excursion> findAllByCruiseId(long id);
}
