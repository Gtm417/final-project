package com.rf.springsecurity.entity.port;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

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

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", excursionName='" + excursionName + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", port=" + port +
                '}';
    }
}
