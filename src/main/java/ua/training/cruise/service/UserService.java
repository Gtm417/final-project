package ua.training.cruise.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.training.cruise.dto.BalanceDTO;
import ua.training.cruise.dto.RegistrationDTO;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.repository.UserRepository;
import ua.training.cruise.service.mapper.UserMapper;


@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User saveNewUser(@NonNull RegistrationDTO registrationDTO) throws DataBaseDuplicateConstraint {
        try {
            return userRepository.save(userMapper.mapToEntity(registrationDTO));
        } catch (DataIntegrityViolationException ex) {
            throw new DataBaseDuplicateConstraint("User with such login already exist:", registrationDTO.getLogin());
        }
    }

    public User getUserByLogin(@NonNull String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login).
                orElseThrow(() -> new UsernameNotFoundException("There is no user with login: " + login));
    }


    public User addBalance(User user, BalanceDTO balanceDTO) {
        user.setBalance(user.getBalance() + balanceDTO.getBalance());
        return userRepository.save(user);
    }


}
