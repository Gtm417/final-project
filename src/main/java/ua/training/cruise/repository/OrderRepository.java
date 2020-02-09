package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCruise(Cruise cruise);

    List<Order> findAllByUser(User user);
}
