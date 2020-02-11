package ua.training.cruise.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role roles;

    @Column(name = "balance", nullable = false, columnDefinition = "bigint default 0")
    private long balance;
}