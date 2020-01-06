package com.rf.springsecurity.domain.ports;

import com.rf.springsecurity.domain.orders.Order;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table( name="excursions",
        uniqueConstraints={@UniqueConstraint(columnNames={"excursion_name"})})
public class Excursion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "excursion_name")
    private String excursionName;

    private int duration;
    private long price;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="port_ID")
    private Port port;

    public Excursion(long id, String excursionName, int duration, long price) {
        this.id = id;
        this.excursionName =excursionName;
        this.duration = duration;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", excursionName='" + excursionName + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", port= " + port +
                '}';
    }
}
