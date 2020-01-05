package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.users.User;
import com.rf.springsecurity.dto.UsersDTO;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import com.rf.springsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;



@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    public UserService(UserRepository userRepository, UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }

    public void saveNewUser (@NotNull User user){

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getAuthenticatedUser(){
        return getUserByLogin(userAuthenticationService.getAuthenticatedUserDetails().getUsername());
    }

    private User getUserByLogin(@NotNull String login) throws  UsernameNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() -> new  UsernameNotFoundException("There is no user with login: " + login));
    }

    public void updateUser(User user){
        userRepository.save(user);
    }
}
