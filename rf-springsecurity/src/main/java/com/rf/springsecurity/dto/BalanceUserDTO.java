package com.rf.springsecurity.dto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BalanceUserDTO {
    //TODO validation value have to be digit
    @NotNull(message = "balance cannot be empty.")
    @Min(value = 1, message =  "balance cannot be > 0" )
    private Long balance;
}
