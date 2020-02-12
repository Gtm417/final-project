package ua.training.cruise.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Z].{2,10}", message = "Only UpperCase ")
    private String ticketName;

    @NotNull(message = "Required")
    @Positive(message = "More than 0")
    @Max(value = 9999, message = "Less than 10000")
    private Long price;

    @NotNull(message = "Required")
    @Min(value = 0, message = "Between 0 and 100")
    @Max(value = 100, message = "Between 0 and 100")
    private Integer discount;
}
