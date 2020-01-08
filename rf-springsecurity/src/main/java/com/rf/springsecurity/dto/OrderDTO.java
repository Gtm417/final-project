package com.rf.springsecurity.dto;


import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.domain.users.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
    private String cruiseName;
    @NotNull
    private long ticket_id;
    private List<String> excursionNames;
}
