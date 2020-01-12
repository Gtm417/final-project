package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.ports.Excursion;
import com.rf.springsecurity.repository.ExcursionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExcursionService {

    private final ExcursionRepository excursionRepository;

    @Autowired
    public ExcursionService(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    public List<Excursion> findAllByListOfNames(List<String> names){
        if(Objects.isNull(names)){
            return new ArrayList<>();
        }
        Objects.requireNonNull(names);
        return names.stream()
                .filter(Objects::nonNull)
                .map((name) -> excursionRepository.findByExcursionName(name)
                        .orElse(new Excursion(0,"",0, 0)))
                .collect(Collectors.toList());
    }
    public long getTotalPriceOfExcursions(List<Excursion> excursions){
        return excursions.stream().mapToLong(Excursion::getPrice).sum();
    }
}
