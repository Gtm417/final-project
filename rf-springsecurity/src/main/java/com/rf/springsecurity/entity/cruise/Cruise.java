package com.rf.springsecurity.entity.cruise;


import com.rf.springsecurity.entity.port.Port;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table( name="cruises",
        uniqueConstraints={@UniqueConstraint(columnNames={"cruise_name"})})
public class Cruise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "cruise_name", nullable = false)
    private String cruiseName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ship_id")
    private Ship ship;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "ports_cruises",
            joinColumns = @JoinColumn(name = "cruise_id"),
            inverseJoinColumns = @JoinColumn(name = "port_id"))
    private List<Port> ports;

    @OneToMany(mappedBy = "cruise", fetch =  FetchType.EAGER)
    private List<Ticket> tickets;

    @Column(name="departure_date", nullable =  false)
    private LocalDate departureDate;

    @Column(name= "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name="description_eng")
    private String description_eng;

    @Column(name="description_ru")
    private String description_ru;

//    public Cruise(String name, Ship ship, List<Port> ports, List<Ticket> tickets, LocalDate departureDate, LocalDate arrivalDate, String description_eng, String description_ru) {
//        this.cruiseName = name;
//        this.ship = ship;
//        this.ports= ports;
//        this.tickets = tickets;
//        this.departureDate =departureDate;
//        this.arrivalDate = arrivalDate;
//        this.description_eng = description_eng;
//        this.description_ru = description_ru;
//
//    }
}
