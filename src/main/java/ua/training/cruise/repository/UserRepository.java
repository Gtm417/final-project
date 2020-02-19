package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.cruise.entity.user.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}

