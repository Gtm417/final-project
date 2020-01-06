package com.rf.springsecurity.repository;

import com.rf.springsecurity.domain.ports.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion,Long> {
    Optional<Excursion> findByExcursionName(String name);
}
