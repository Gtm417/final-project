package ua.training.cruise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruiseDescriptionsDTO {
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[\\w\\d\\s\\v \\-,.!'\"]*", message = "Any character without \"(,),{,},[,],<,>\" ")
    private String description_eng;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-ZА-Яa-zа-я\\d\\s\\v \\-,.!'\"]*", message = "Any character without \"(,),{,},[,],<,>\" ")
    private String description_ru;
}
