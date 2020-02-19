package ua.training.cruise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    @NotEmpty(message = "Required")
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,16}$",
            message = "Login should contains latin letters and numbers. Min value of characters 3. Max value of characters 16")
    private String login;

    @NotEmpty(message = "Required")
    @Pattern(regexp = "^[A-Za-z0-9_-]{5,18}$",
            message = "Password should contains latin letters and numbers. Min value of characters 6. Max value of characters 18")
    private String password;
}
