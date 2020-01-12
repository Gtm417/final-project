package com.rf.springsecurity.repository;

import com.rf.springsecurity.entity.ports.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion,Long> {
    Optional<Excursion> findByExcursionName(String name);
    @Query("SELECT e FROM Cruise c LEFT JOIN c.ports s LEFT JOIN s.excursions e WHERE c.id = :id")
    List<Excursion> findAllByCruiseID(@Param("id") long id);
}