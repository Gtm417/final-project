package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.users.User;
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

    public void addBalance(long balance){
        User user = userService.getAuthenticatedUser();
        user.setBalance(user.getBalance() + balance);
        userService.updateUser(user);
    }
    public void subBalance(long balance) throws NotEnoughMoney {
        User user = userService.getAuthenticatedUser();
        long totalBalance = user.getBalance() - balance;
        if (totalBalance < 0) {
            throw new NotEnoughMoney("Not Enough Money " + user.getBalance());
        }
        user.setBalance(user.getBalance() - balance);
        userService.updateUser(user);
    }

    public BalanceUserDTO getUserBalance(){
        User user = userService.getAuthenticatedUser();
        return new BalanceUserDTO(user.getBalance());
    }



}
