package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.users.User;
import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BalanceService {

    private final UserService userService;

    @Autowired
    public BalanceService(UserService userService) {
        this.userService = userService;
    }


    //TODO передавать сюда Юзера
    //TODO сново тащит юзера это плохо он уже у меня должен быть
    public void addBalance(long balance){
        User user = userService.getAuthenticatedUser();
        user.setBalance(user.getBalance() + balance);
        userService.updateUser(user);
    }

    //TODO передавать сюда Юзера
    //TODO сново тащит юзера это плохо он уже у меня должен быть
    public void subBalance(long balance) throws NotEnoughMoney {
        User user = userService.getAuthenticatedUser();
        long totalBalance = user.getBalance() - balance;
        if (totalBalance < 0) {
            throw new NotEnoughMoney("Not Enough Money " + user.getBalance());
        }
        user.setBalance(user.getBalance() - balance);
        userService.updateUser(user);
    }

//мб  нев нужен вообще
    public BalanceUserDTO getUserBalance(){
        //TODO can find User by name from controller
        User user = userService.getAuthenticatedUser();
        return new BalanceUserDTO(user.getBalance());
    }



}
