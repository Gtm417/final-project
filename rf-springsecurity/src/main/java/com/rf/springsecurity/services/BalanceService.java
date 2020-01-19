package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BalanceService {

    //todo balance service in userservice

    private final UserService userService;

    @Autowired
    public BalanceService(UserService userService) {
        this.userService = userService;
    }


//мб  нев нужен вообще
    public BalanceUserDTO getUserBalance(){
        //TODO can find User by name from controller
        User user = userService.getAuthenticatedUser();
        return new BalanceUserDTO(user.getBalance());
    }



}
