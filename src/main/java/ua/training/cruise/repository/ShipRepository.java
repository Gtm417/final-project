package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.cruise.entity.cruise.Ship;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
