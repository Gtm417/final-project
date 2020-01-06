package com.rf.springsecurity.dto;

import com.rf.springsecurity.domain.ports.Excursion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ExcursionsDTO {
    List<Excursion> excursionsDTO;
}
