package com.rf.springsecurity.entity.order;


import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.cruise.Ticket;
import com.rf.springsecurity.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;
//
//    @Column(name="price", nullable = false)
//    private String orderPrice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Cruise cruise;

    @ManyToOne(fetch = FetchType.EAGER,  optional = false)
    private Ticket ticket;
}
