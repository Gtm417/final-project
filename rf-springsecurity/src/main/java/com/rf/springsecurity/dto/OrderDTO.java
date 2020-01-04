package com.rf.springsecurity.dto;


import com.rf.springsecurity.domain.cruises.Ticket;
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
    String userLogin;
    String cruiseName;
    Ticket ticket;

}
