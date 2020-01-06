package com.rf.springsecurity.domain.cruises;

import com.rf.springsecurity.domain.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "ticket_name", nullable = false)
    private String ticketName;

    @Column(nullable = false)
    private long price;

    @ManyToMany(mappedBy = "tickets",fetch = FetchType.LAZY)
    private List<Cruise> cruise;

    @OneToMany(mappedBy = "ticket")
    private List<Passenger> listOfPassenger;

    @OneToMany(mappedBy = "ticket")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketName='" + ticketName + '\'' +
                ", price=" + price +
                '}';
    }
}
