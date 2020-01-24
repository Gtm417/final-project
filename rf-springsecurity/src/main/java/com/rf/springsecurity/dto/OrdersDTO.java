package com.rf.springsecurity.dto;

import com.rf.springsecurity.entity.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private String firstName;
    private String secondName;
    private String ticketName;
    private String cruiseName;
}
