package com.javasampleapproach.springrest.postgresql.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    @Qualifier("oauth2authSuccessHandler")
    private AuthenticationSuccessHandler oauth2authSuccessHandler;
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //qui si definisce quali endpoints hanno bisogno del login e quali no

        httpSecurity
                .cors()
                //.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .antMatcher("/**").authorizeRequests()
                //.antMatchers("/api/v1/stabilimenti/**","/oauth/token")
                //scommentare riga sotto per rimuovere oauth
                .antMatchers("/api/v1/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/api/v1/stabilimenti")
                //.logoutSuccessHandler(oauth2logoutSuccessHandler)
                //.invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint()
                //.baseUri("/oauth2/callback/**")
                .and()
                .successHandler(oauth2authSuccessHandler);
        //httpSecurity.logout().logoutUrl("/api/v1/logout");
    }

}

