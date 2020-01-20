package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.NonNull;



@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser (@NonNull User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getAuthenticatedUser(){
        return getUserByLogin(getAuthenticatedUserDetails().getUsername());
    }

    private UserDetails getAuthenticatedUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }

    private User getUserByLogin(@NonNull String login) throws  UsernameNotFoundException {
        return userRepository.findByLogin(login).
                orElseThrow(() -> new  UsernameNotFoundException("There is no user with login: " + login));
    }

    public User addBalance(User user, long balance){ ;
        user.setBalance(user.getBalance() + balance);
        return userRepository.save(user);
    }


}
