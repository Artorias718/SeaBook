package com.javasampleapproach.springrest.postgresql.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //qui si definisce quali endpoints hanno bisogno del login e quali no

        httpSecurity
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/").permitAll();
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();

//        httpSecurity
//                .authorizeRequests().antMatchers("/**")
//                .permitAll().anyRequest().anonymous();//<< this will allow any resource endpoint access when the HTTP
        // request Authorization header not available
        //http.authorizeRequests().antMatchers("/**").permitAll();<< also can

    }
}
