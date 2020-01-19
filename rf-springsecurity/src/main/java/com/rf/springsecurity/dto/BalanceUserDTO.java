package com.rf.springsecurity.dto;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BalanceUserDTO{
    @NotNull(message = "Balance cannot be empty.")
    @Positive(message = "Balance cannot be <= 0")
    @Digits(message = "Only digits required", integer = 4, fraction = 0 )
    private Long balance;
}
