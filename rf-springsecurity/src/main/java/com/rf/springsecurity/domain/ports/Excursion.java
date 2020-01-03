package com.rf.springsecurity.domain.ports;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString


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

    private String duration;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="port_ID")
    private Port port;

}
