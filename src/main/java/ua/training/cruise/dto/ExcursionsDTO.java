package ua.training.cruise.dto;

import ua.training.cruise.entity.port.Excursion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


@Data
@AllArgsConstructor
public class ExcursionsDTO {
    private Set<Excursion> excursionsDTO;
}
