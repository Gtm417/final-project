package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.training.cruise.entity.cruise.Cruise;

import java.util.Optional;

@Repository
public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    Optional<Cruise> findByCruiseName(String cruiseName);
}
