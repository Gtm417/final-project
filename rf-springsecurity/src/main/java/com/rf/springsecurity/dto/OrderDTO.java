package com.rf.springsecurity.dto;


import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.users.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    String firstName;
    String secondName;
    User user;
    Cruise cruise;
    Ticket ticket;

}
