package com.andres.notaVenta.security;


import com.andres.notaVenta.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/notasVentas/listar", "/vendedores/**").hasRole("ADMIN")
                        .requestMatchers("/productos/create", "/productos/delete/**",
                                "/productos/save").hasRole("VENDEDOR")
                        .anyRequest().authenticated()

                ).formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                        .logout(config -> config.logoutSuccessUrl("/login")
                        .permitAll()
                );
                return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
