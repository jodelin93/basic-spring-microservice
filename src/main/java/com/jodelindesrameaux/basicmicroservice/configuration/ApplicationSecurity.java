package com.jodelindesrameaux.basicmicroservice.configuration;

import com.jodelindesrameaux.basicmicroservice.security.AuthenticationFilter;
import com.jodelindesrameaux.basicmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilter(authenticationFilter());
        http.authorizeRequests().mvcMatchers("/users/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }



    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter= new AuthenticationFilter(userService,authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/user/login");

        return authenticationFilter;
    }
}
