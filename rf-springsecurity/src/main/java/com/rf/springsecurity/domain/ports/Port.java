package com.rf.springsecurity.domain.ports;


import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Ship;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString


@Entity
@Table( name="ports",
        uniqueConstraints={@UniqueConstraint(columnNames={"port_name"})})

public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;
    @Column(name = "port_name")
    private String portName;

    @OneToMany(mappedBy = "port")
    private List<Excursion> excursions;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Cruise> cruises;
}
