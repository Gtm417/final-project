package com.rf.springsecurity.repository;

import com.rf.springsecurity.dto.OrdersDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.order.Order;
import com.rf.springsecurity.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o, t, c FROM Order o JOIN o.cruise c JOIN o.ticket t WHERE c = :cruise")
    List<Order> findAllByCruise(Cruise cruise);

    @Query("select new com.rf.springsecurity.dto.OrdersDTO(o.firstName,o.secondName,o.ticket.ticketName,o.cruise.cruiseName) " +
            "from Order o WHERE o.user =:user")
    List<OrdersDTO> findAllByUser(User user);
}
