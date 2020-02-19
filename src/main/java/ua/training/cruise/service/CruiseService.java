package ua.training.cruise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.exception.UnsupportedCruise;
import ua.training.cruise.repository.CruiseRepository;

import java.util.List;

@Service
public class CruiseService {

    private final CruiseRepository cruiseRepository;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository) {
        this.cruiseRepository = cruiseRepository;
    }

    public List<Cruise> getAllCruises() {
        return cruiseRepository.findAll();
    }

    public Cruise changeCruiseDescription(Cruise cruise, CruiseDescriptionsDTO cruiseDescriptionsDTO) {
        cruise.setDescription_ru(cruiseDescriptionsDTO.getDescription_ru());
        cruise.setDescription_eng(cruiseDescriptionsDTO.getDescription_eng());
        return cruiseRepository.save(cruise);
    }


    public Cruise findCruiseById(Long id) {
        return cruiseRepository.findById(id)
                .orElseThrow(() -> new UnsupportedCruise("Cruise not found with id: " + id));
    }


}
