package ua.training.cruise.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.dto.BalanceDTO;
import ua.training.cruise.dto.RegistrationDTO;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.repository.UserRepository;
import ua.training.cruise.service.mapper.UserMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserService.class)
public class UserServiceTest {

    public static final String LOGIN = "test";
    private static final User USER = User.builder()
            .login("test")
            .password("test")
            .roles(Role.ROLE_USER)
            .balance(1000L)
            .build();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    UserService service;
    @MockBean
    UserRepository repository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserMapper mapper;

    @Test
    public void saveNewUser() throws DataBaseDuplicateConstraint {
        RegistrationDTO dto = new RegistrationDTO("test", "test");
        when(mapper.mapToEntity(dto)).thenReturn(USER);
        when(repository.save(any(User.class))).thenReturn(USER);


        User actual = service.saveNewUser(dto);
        verify(repository, times(1)).save(any(User.class));
        Assert.assertEquals(USER, actual);
    }

    @Test(expected = DataBaseDuplicateConstraint.class)
    public void saveNewUserIfExistInDataBase() throws DataBaseDuplicateConstraint {
        RegistrationDTO dto = new RegistrationDTO("test", "test");

        when(mapper.mapToEntity(dto)).thenReturn(USER);
        when(repository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("test"));

        service.saveNewUser(dto);
        verify(repository, times(1)).save(USER);
    }


    @Test
    public void addBalance() {
        BalanceDTO balanceDTO = new BalanceDTO();
        long inputBalance = 500L;
        balanceDTO.setBalance(inputBalance);

        when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(USER);
        service.addBalance(USER, balanceDTO);

        verify(repository, times(1)).save(USER);
        Assert.assertEquals(1500L, USER.getBalance());
    }

    @Test
    public void getUserByLoginIfExist() {
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.of(USER));

        User actual = service.getUserByLogin(LOGIN);

        verify(repository, times(1)).findByLogin(LOGIN);
        Assert.assertEquals(USER, actual);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void getUserByLoginIfNotFound() {
        when(repository.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        service.getUserByLogin(LOGIN);
    }


}