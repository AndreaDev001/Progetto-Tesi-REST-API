package com.progettotirocinio.restapi.config;


import com.progettotirocinio.restapi.authentication.AuthenticationFilter;
import com.progettotirocinio.restapi.authentication.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    private final AuthenticationFilter authenticationFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling(customizer -> customizer.authenticationEntryPoint(restAuthenticationEntryPoint));
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
                        .requestMatchers("/taskAssignments/public/**").permitAll()
                        .requestMatchers("/pollOptions/public/**").permitAll()
                        .requestMatchers("/pollVotes/public/**").permitAll()
                        .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        httpSecurity.addFilterAfter(authenticationFilter,BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
