package ua.training.cruise.service;

import ua.training.cruise.entity.user.User;
import ua.training.cruise.repository.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
public class UserAuthenticationService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new  UsernameNotFoundException("There is no user with login: " + login));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.singleton(user.getRoles()));
    }

    public void autoLogin(String username, String password){
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(String.format("Successfully %s auto logged in", username));
        }
    }


}
