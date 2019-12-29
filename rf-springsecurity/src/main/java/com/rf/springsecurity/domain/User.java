package com.rf.springsecurity.domain;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString


@Entity
@Table( name="user",
        uniqueConstraints={@UniqueConstraint(columnNames={"login"})})
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}