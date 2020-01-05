package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyCruiseServiceTest {

    @Autowired
    private BuyCruiseService buyCruiseService;
    @Test
    public void buy() {
        OrderDTO orderDTO = OrderDTO.builder()
                //.cruiseName("name1")
                .firstName("tima")
                .secondName("tima")
                .ticket(Ticket.VIP)
                //.userLogin("t")
                .build();
        try {
            buyCruiseService.buy(orderDTO);
        } catch (UnsupportedCruiseName unsupportedCruiseName) {
            unsupportedCruiseName.printStackTrace();
        }
    }
}
