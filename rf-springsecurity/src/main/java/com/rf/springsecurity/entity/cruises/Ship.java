package com.rf.springsecurity.entity.cruises;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
@Table( name="ships",
        uniqueConstraints={@UniqueConstraint(columnNames={"ship_name"})})
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "ship_name", nullable = false)
    private String shipName;

    @Column(name = "max_amount_of_passenger", nullable = false)
    private int maxAmountOfPassenger;

    @Column(name = "current_amount_of_passenger", nullable = false)
    private int currentAmountOfPassenger;

    @OneToMany(mappedBy = "ship")
    private List<Passenger> listOfPassenger;

    @OneToOne(mappedBy = "ship",cascade = CascadeType.ALL)
    private Cruise cruise;
}
