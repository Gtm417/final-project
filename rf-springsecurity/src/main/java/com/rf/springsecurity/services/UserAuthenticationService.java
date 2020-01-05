package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.users.User;
import com.rf.springsecurity.exceptions.UnhandledUserName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAuthenticationService {

    private UserService userService;

    @Autowired
    public UserAuthenticationService(UserService userService) {
        this.userService = userService;
    }


    public UserDetails getAuthenticatedUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }

    public User getAuthenticatedUser() throws UnhandledUserName {
        return userService.getUserByLogin(getAuthenticatedUserDetails().getUsername());
    }

    public void autoLogin(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(String.format("Successfully %s auto logged in", username));
        }
    }


}
