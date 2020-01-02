package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.User;
import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class BalanceService {
    UserRepository userRepository;

    @Autowired
    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO may be remove throws
    public void updateBalance(String login, long balance) throws Exception{

        User user = getUserByLogin(login);
        user.setBalance(user.getBalance() + balance);
        userRepository.save(user);

    }

    public BalanceUserDTO getUserBalance(String login){
        User user = getUserByLogin(login);
        //System.out.println(user.getBalance());
        return new BalanceUserDTO(user.getLogin(),user.getBalance());
    }

    private User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }

}
