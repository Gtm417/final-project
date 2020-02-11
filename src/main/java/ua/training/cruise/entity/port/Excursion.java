package ua.training.cruise.entity.port;

import lombok.*;
import ua.training.cruise.entity.order.Order;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "excursions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"excursion_name"})})
public class Excursion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "excursion_name")
    private String excursionName;

    private int duration;
    private long price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "port_ID")
    private Port port;

    @ManyToMany(mappedBy = "excursions", fetch = FetchType.LAZY)
    private Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Excursion excursion = (Excursion) o;
        return id == excursion.id &&
                excursionName.equals(excursion.excursionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, excursionName);
    }

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
