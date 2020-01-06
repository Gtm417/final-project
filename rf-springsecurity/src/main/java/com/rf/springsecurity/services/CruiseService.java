package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.domain.ports.Port;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CruiseService {

    private CruiseRepository cruiseRepository;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository) {
        this.cruiseRepository =cruiseRepository;
    }

    public Cruise getCruiseDataByName(String name) throws UnsupportedCruiseName {
        return cruiseRepository.findByCruiseName(name)
                .orElseThrow(() -> new UnsupportedCruiseName(name));
    }

    public List<Cruise> getAllCruises(){
        return cruiseRepository.findAll();
    }

    public List<Excursion> getAllExcursionsInCruise(Cruise cruise){
        ArrayList<Excursion> excursions = new ArrayList<>();
        for (Port port : cruise.getPorts()) {
            excursions.addAll(port.getExcursions());
        }
        //System.err.println(excursions);
        //  cruise.getPorts().stream().
        return excursions;
    }
}
