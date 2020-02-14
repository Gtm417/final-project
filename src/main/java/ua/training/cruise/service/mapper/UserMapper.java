package ua.training.cruise.service.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.training.cruise.dto.RegistrationDTO;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;

@Component
public class UserMapper {

    public User mapToEntity(RegistrationDTO registrationDTO) {
        return User.builder()
                .login(registrationDTO.getLogin())
                .password(new BCryptPasswordEncoder().encode(registrationDTO.getPassword()))
                .roles(Role.ROLE_USER).active(true)
                .build();
    }
}
