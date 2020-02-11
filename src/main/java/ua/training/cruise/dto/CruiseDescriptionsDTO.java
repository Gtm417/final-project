package ua.training.cruise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CruiseDescriptionsDTO {
    private String description_eng;
    private String description_ru;
}
