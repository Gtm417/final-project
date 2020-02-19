package ua.training.cruise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    @NotNull(message = "Required")
    @Digits(integer = 5, fraction = 0, message = "Less than 5 chars")
    @Min(value = 100, message = "More than 100")
    @Max(value = 9999, message = "Less than 10000")
    private Long balance;
}
