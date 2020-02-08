package ua.training.cruise.repository;

import ua.training.cruise.dto.OrdersDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCruise(Cruise cruise);

    @Query("select new ua.training.cruise.dto.OrdersDTO(o.firstName,o.secondName,o.ticket.ticketName,o.cruise.cruiseName, o.orderPrice) " +
            "from Order o WHERE o.user =:user")
    List<OrdersDTO> findAllByUser(User user);
}
