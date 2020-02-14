package ua.training.cruise.dto;

import lombok.Data;
import ua.training.cruise.entity.cruise.Ticket;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class OrderDTO {

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Z][a-z]+", message = "First character should be upper case")
    private String firstName;

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Z][a-z]+", message = "First character should be upper case")
    private String secondName;

    @NotNull(message = "Required")
    private Ticket ticket;
}
