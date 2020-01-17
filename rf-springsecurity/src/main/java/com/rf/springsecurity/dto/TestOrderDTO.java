package com.rf.springsecurity.dto;

import com.rf.springsecurity.entity.cruise.Ticket;
import com.rf.springsecurity.entity.port.Excursion;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestOrderDTO {
    private String firstName;
    private String secondName;
    private Ticket ticket;
    private List<Excursion> excursions;

//    public TestOrderDTO(List<Excursion> excursionsDTO) {
//        this.excursionsDTO = excursionsDTO;
//    }
}

