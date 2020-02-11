package ua.training.cruise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.exception.NotFoundExcursion;
import ua.training.cruise.repository.ExcursionRepository;

import java.util.List;

@Service
public class ExcursionService {

    private final ExcursionRepository excursionRepository;

    @Autowired
    public ExcursionService(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    public Excursion findById(long id) throws NotFoundExcursion {
        return excursionRepository.findById(id)
                .orElseThrow(() -> new NotFoundExcursion("Excursion not found with id: ", id));
    }

    public List<Excursion> getAllExcursionsByCruiseId(Long id) {
        return excursionRepository.findAllByCruiseID(id);
    }

}
