package com.rf.springsecurity.domain.cruises;


import com.rf.springsecurity.domain.ports.Port;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
@Table( name="cruises",
        uniqueConstraints={@UniqueConstraint(columnNames={"cruise_name"})})
public class Cruise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "cruise_name", nullable = false)
    private String cruiseName;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name="ship_id")
    private Ship ship;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
            name = "ports_cruises",
            joinColumns = @JoinColumn(name = "cruise_id"),
            inverseJoinColumns = @JoinColumn(name = "port_id"))
    private List<Port> ports;

    @Column(name= "low_ticket_price",nullable = false)
    private long lowPrice;

    @Column(name= "casual_ticket_price",nullable = false)
    private long casualPrice;

    @Column(name= "VIP_ticket_price",nullable = false)
    private long VIPPrice;
    @Column(name="departure_date", nullable =  false)
    private LocalDate departureDate;
    @Column(name= "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", cruiseName='" + cruiseName + '\'' +
                ", ship=" + ship +
                ", ports=" + ports +
                ", lowPrice=" + lowPrice +
                ", casualPrice=" + casualPrice +
                ", VIPPrice=" + VIPPrice +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
