package com.ceica.UF2405.config;

import com.ceica.UF2405.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService){
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/register").permitAll()
                        .requestMatchers("/js/**","/css/**","/img/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/books")
                        .permitAll()
                )
                .logout((logout) -> logout
                .logoutUrl("/logout") // Ruta de logout
                .logoutSuccessUrl("/") // Página a la que redirigir después del logout
                .invalidateHttpSession(true) // Invalidar la sesión
                .deleteCookies("JSESSIONID") // Eliminar las cookies, si las hubiera
                );

        return http.build();
    }





}