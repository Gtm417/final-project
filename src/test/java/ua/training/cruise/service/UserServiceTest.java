package ua.training.cruise.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    public static final User USER = User.builder()
            .login("test")
            .password("test")
            .roles(Role.ROLE_USER)
            .balance(1000L)
            .build();
    public static final String LOGIN = "test";
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    UserService service;
    @MockBean
    UserRepository repository;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    public void saveNewUser() {
    }

    @Test
    public void addBalance() {
        long inputBalance = 500L;

        when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(USER);
        service.addBalance(USER, inputBalance);

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