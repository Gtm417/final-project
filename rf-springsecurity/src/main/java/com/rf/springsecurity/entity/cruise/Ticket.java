package com.rf.springsecurity.entity.cruise;

import com.rf.springsecurity.entity.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

//Todo Двойной первичный ключ (имя и айди круиза)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "ticket_name", nullable = false)
    private String ticketName;

    @Column(nullable = false)
    private long price;

    @Column(name="discount", nullable = false, columnDefinition = "int default 0")
    private int discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cruise_id")
    private Cruise cruise;

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
                ", discount=" + discount +
                '}';
    }
}
