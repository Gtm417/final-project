package ua.training.cruise.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.repository.UserRepository;

import java.util.Collections;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with login: " + login));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.singleton(user.getRoles()));
    }


}
