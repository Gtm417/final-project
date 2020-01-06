package com.rf.springsecurity.dto;


import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.domain.users.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    private String firstName;
    private String secondName;
    private User user;
    private Cruise cruise;
    private Ticket ticket;
    private List<Excursion> excursions;
}
