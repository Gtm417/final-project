package ua.training.cruise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private String firstName;
    private String secondName;
    private String ticketName;
    private String cruiseName;
    private long orderPrice;
}
