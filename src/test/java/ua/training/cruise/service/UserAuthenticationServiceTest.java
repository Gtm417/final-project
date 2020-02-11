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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthenticationServiceTest {
    public static final User USER = User.builder()
            .login("test")
            .password("test")
            .roles(Role.ROLE_USER)
            .build();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    UserAuthenticationService service;
    @MockBean
    UserRepository userRepository;

    @Test
    public void loadUserByUsername() {
        String login = "test";
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(USER));

        UserDetails actual = service.loadUserByUsername(login);
        UserDetails expected =
                new org.springframework.security.core.userdetails.User(USER.getLogin(), USER.getPassword(), Collections.singleton(USER.getRoles()));

        verify(userRepository, times(1)).findByLogin(login);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameIfNotFound() {
        String login = "test";
        when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        service.loadUserByUsername(login);

    }
}