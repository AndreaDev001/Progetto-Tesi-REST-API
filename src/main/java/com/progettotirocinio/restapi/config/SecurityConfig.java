package com.progettotirocinio.restapi.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/documentation/**").permitAll()
                        .requestMatchers("/tasks/public/**").permitAll()
                        .requestMatchers("/users/public/**").permitAll()
                        .requestMatchers("/boards/public/**").permitAll()
                        .requestMatchers("/boardInvites/public/**").permitAll()
                        .requestMatchers("/boardMembers/public/**").permitAll()
                        .requestMatchers("/comments/public/**").permitAll()
                        .requestMatchers("/polls/public/**").permitAll()
                        .requestMatchers("/roles/public/**").permitAll()
                        .requestMatchers("/roleOwners/public/**").permitAll()
                        .requestMatchers("/tags/public/**").permitAll()
                        .requestMatchers("/taskGroups/public/**").permitAll()
                        .requestMatchers("/teams/public/**").permitAll()
                        .requestMatchers("/teamMembers/public/**").permitAll()
                        .requestMatchers("/commentLikes/public/**").permitAll()
                        .requestMatchers("/discussionLikes/public/**").permitAll()
                        .requestMatchers("/taskLikes/public/**").permitAll()
                        .requestMatchers("/pollLikes/public/**").permitAll()
                        .requestMatchers("/boardImages/public/**").permitAll()
                        .requestMatchers("/reports/public/**").permitAll()
                        .requestMatchers("/taskReports/public/**").permitAll()
                        .requestMatchers("/pollReports/public/**").permitAll()
                        .requestMatchers("/discussionReports/public/**").permitAll()
                        .requestMatchers("/commentReports/public/**").permitAll()
                        .requestMatchers("/bans/public/**").permitAll()
                        .requestMatchers("/boardBans/public/**").permitAll()
                        .requestMatchers("/images/public/**").permitAll()
                        .requestMatchers("/taskImages/public/**").permitAll()
                        .requestMatchers("/userImages/public/**").permitAll()
                        .anyRequest().permitAll());
        return httpSecurity.build();
    }
}
