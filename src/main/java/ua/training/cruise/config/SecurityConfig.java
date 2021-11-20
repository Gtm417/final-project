package ua.training.cruise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.training.cruise.service.UserAuthenticationService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public SecurityConfig(UserAuthenticationService userService) {
        this.userAuthenticationService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login", "/registration", "/users", "/users/**").anonymous()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/home","/get/localitiesGet/by/localitySend/id").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/user/user-profile", true).loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/login/error");

//        http
//                .authorizeRequests()
//                .antMatchers("/", "/login", "/registration", "/users", "/users/**").anonymous()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().defaultSuccessUrl("/user", true).loginPage("/login")
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/user/error");

    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**",
                        "/js/**", "/images/**", "/scss/**", "/vendor/**", "/img/favicon.ico");

    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userAuthenticationService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

}
