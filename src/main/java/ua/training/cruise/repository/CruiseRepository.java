package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.cruise.entity.cruise.Cruise;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {
}
