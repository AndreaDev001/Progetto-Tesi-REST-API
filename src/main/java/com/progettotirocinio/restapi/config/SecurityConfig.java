package com.progettotirocinio.restapi.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/documentation/**").permitAll());
        return httpSecurity.build();
    }
}
