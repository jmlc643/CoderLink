package com.upao.pe.coderlink.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Configure the security
    // HttpSecurity is very important
    @Bean
    public SecurityFilterChain filterChai(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                // Permit request to all endpoints of AuthController without authentication
                authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
                // Show form authentication for all
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    //session.invalidSessionUrl("/auth/login/");
                    session.maximumSessions(1);
                })
                .build();
    }
}
