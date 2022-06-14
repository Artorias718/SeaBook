package com.javasampleapproach.springrest.postgresql.configs;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("oauth2authSuccessHandler")
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    CustomerRepository repository;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String firstName = null;

        if (repository.findByEmail(authenticationToken.getPrincipal().getAttributes().get("email").toString()) == null) {
            firstName = authenticationToken.getPrincipal().getAttributes().get("given_name").toString();
            String email = authenticationToken.getPrincipal().getAttributes().get("email").toString();

            Customer user = new Customer(firstName, email);
            repository.save(user);
        }

        String cookieValue = request.getSession().getId();

        // this.redirectStrategy.sendRedirect(request, response, "http://localhost:3000/oauth2/redirect?token=" + cookieValue);
        this.redirectStrategy.sendRedirect(request, response, "http://localhost:3000/oauth2/redirect");
    }




}
