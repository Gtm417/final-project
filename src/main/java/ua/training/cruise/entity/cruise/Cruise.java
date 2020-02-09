package ua.training.cruise.entity.cruise;


import lombok.*;
import ua.training.cruise.entity.port.Port;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ship_id")
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

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", cruiseName='" + cruiseName + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", description_eng='" + description_eng + '\'' +
                ", description_ru='" + description_ru + '\'' +
                '}';
    }
}
