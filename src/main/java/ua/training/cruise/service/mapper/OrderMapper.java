package ua.training.cruise.service.mapper;

import org.springframework.stereotype.Component;
import ua.training.cruise.dto.OrderDTO;
import ua.training.cruise.entity.order.Order;

import java.util.HashSet;

@Component
public class OrderMapper {
    public Order mapToEntity(OrderDTO orderDTO) {
        return Order.builder()
                .firstName(orderDTO.getFirstName())
                .secondName(orderDTO.getSecondName())
                .ticket(orderDTO.getTicket())
                .excursions(new HashSet<>())
                .orderPrice(orderDTO.getTicket().getPriceWithDiscount())
                .build();
    }
}
