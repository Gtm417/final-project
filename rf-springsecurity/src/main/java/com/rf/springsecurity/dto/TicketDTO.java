package com.rf.springsecurity.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private String ticketName;
    private long price;
    private int discount;
}
