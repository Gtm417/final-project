package com.rf.springsecurity.domain.cruises;


import com.rf.springsecurity.domain.ports.Port;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString


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

    @ManyToMany(mappedBy = "cruises",fetch = FetchType.EAGER)
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

}
