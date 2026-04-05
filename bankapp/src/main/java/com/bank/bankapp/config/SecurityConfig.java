package com.bank.bankapp.config;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bank.bankapp.security.JwtFilter;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private JwtFilter jwtFilter;

     @Bean
     public AuthenticationManager authenticationManager( AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
     }
     @Bean
    public PasswordEncoder passwordEncoder(){
          return  new BCryptPasswordEncoder();
    }
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // disable CSRF (for testing APIs)
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register").permitAll()  // allow register
                .requestMatchers("/api/auth/**").permitAll()
                 .anyRequest().authenticated() // rest of  sab secure
            )
             .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable()) // disable default login form
            .httpBasic(basic -> basic.disable()); // disable basic auth

        return http.build();
    }

   
}
