package com.rf.springsecurity.dto;

import com.rf.springsecurity.entity.port.Excursion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
public class ExcursionsDTO {
    //TODO SET
    private Set<Excursion> excursionsDTO;
}
