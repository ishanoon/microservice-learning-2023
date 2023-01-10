package com.example.apigatewayservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorisation ->
                        authorisation
                                .pathMatchers("/eureka/**","/api/v1/order/addOrder","/api/v1/inventory/*")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

        return httpSecurity.build();
    }
}
