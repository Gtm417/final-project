package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.users.User;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import com.rf.springsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;

@Slf4j
@Service
public class UserAuthenticationService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDetails getAuthenticatedUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }


    @Override
    public UserDetails loadUserByUsername(@NotNull String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new  UsernameNotFoundException("There is no user with login: " + login));

        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), Collections.singleton(user.getRoles()));
    }

    public void autoLogin(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(String.format("Successfully %s auto logged in", username));
        }
    }


}
