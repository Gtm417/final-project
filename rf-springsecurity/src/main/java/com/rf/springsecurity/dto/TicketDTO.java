package com.rf.springsecurity.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class TicketDTO {

    @NotEmpty(message = "Name is required")
    private String ticketName;

    @NotNull(message = "price cannot be empty")
    @Min(value = 0)
    private long price;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private int discount;
}
