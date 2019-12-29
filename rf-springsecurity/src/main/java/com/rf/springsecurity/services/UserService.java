package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.User;
import com.rf.springsecurity.dto.UsersDTO;
import com.rf.springsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;



@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
     User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
     return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), Collections.singleton(user.getRoles()));
    }



    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }


    public User saveNewUser (User user) throws Exception{
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
