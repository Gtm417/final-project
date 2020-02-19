package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.cruise.entity.port.Excursion;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    @Query("SELECT e FROM Cruise c JOIN c.ports s JOIN s.excursions e WHERE c.id = :id")
    List<Excursion> findAllByCruiseID(@Param("id") long id);
}
