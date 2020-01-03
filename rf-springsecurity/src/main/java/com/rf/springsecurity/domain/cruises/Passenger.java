package com.rf.springsecurity.domain.cruises;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table( name="passengers",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})

public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ticket ticket;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ship_id")
    private Ship ship;

}
