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
//TODO rename
public class OrderDTO {
    @NotNull
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "The name should contain only Latin characters and begin with a upper case character"
    )
    private String firstName;
    @NotNull
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "The name should contain only Latin characters and begin with a upper case character"
    )
    private String secondName;
    private Ticket ticket;
    private List<Excursion> excursions;

//    public TestOrderDTO(List<Excursion> excursionsDTO) {
//        this.excursionsDTO = excursionsDTO;
//    }
}

