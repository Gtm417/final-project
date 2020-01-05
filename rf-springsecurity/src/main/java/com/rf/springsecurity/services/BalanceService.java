package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.users.User;
import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import com.rf.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
public class BalanceService {

    private final UserService userService;

    @Autowired
    public BalanceService(UserService userService) {
        this.userService = userService;
    }

    public void updateBalance(long balance){
        User user = userService.getAuthenticatedUser();
        user.setBalance(user.getBalance() + balance);
        userService.updateUser(user);
    }

    public BalanceUserDTO getUserBalance(){
        User user = userService.getAuthenticatedUser();
        return new BalanceUserDTO(user.getBalance());
    }



}
