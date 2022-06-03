package com.javasampleapproach.springrest.postgresql.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    @Qualifier("oauth2authSuccessHandler")
    private AuthenticationSuccessHandler oauth2authSuccessHandler;
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //qui si definisce quali endpoints hanno bisogno del login e quali no

        httpSecurity
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(oauth2authSuccessHandler);

        //httpSecurity.logout().logoutUrl("/api/v1/logout");
    }

}

