package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.cruise.entity.cruise.Cruise;

import java.util.Optional;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    Optional<Cruise> findByCruiseName(String cruiseName);
}
