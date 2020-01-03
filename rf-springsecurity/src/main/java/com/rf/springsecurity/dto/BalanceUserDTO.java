package com.rf.springsecurity.dto;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BalanceUserDTO {
    //TODO validation value have to be digit
    @NotNull(message = "balance cannot be empty.")
    @Min(value = 0, message =  "balance cannot be < 0" )
    private Long balance;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
