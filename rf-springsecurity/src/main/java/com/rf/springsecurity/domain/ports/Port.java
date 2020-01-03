package com.rf.springsecurity.domain.ports;


import com.rf.springsecurity.domain.cruises.Cruise;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


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


    @ManyToMany(mappedBy = "ports",fetch = FetchType.EAGER)
    private List<Cruise> cruises;

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", portName='" + portName + '\'' +
                ", excursions=" + excursions +
                '}';
    }
}
