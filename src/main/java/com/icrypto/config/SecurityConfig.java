package com.icrypto.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${com.icrypto.end_session.uri}")
    private String end_session_uri;
    @Value("${com.icrypto.post_logout_uri}")
    private String post_logout_uri;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/webjars/**", "/css/**", "/favicon.*","/static/**").permitAll()
                .anyRequest().authenticated()
                .and().logout()
                   .logoutUrl("/logout")
	               .invalidateHttpSession(true)
	               .logoutSuccessUrl(end_session_uri+"?post_logout_redirect_uri="+post_logout_uri);
    }

}

