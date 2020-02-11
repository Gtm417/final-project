package ua.training.cruise.entity.cruise;

import lombok.*;
import ua.training.cruise.entity.order.Order;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "ticket",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ticket_name", "cruise_id"})})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "ticket_name", nullable = false)
    private String ticketName;

    @Column(nullable = false)
    private long price;

    @Column(name = "discount", nullable = false, columnDefinition = "int default 0")
    private int discount;

    @Column(name = "final_price", nullable = false)
    private long priceWithDiscount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cruise_id")
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
