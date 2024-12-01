package com.bgnc.bloodgpt.security;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bgnc.bloodgpt.utils.AuthenticationControllerApis.SECURE_CONFIG_PERMIT_ALL;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPoint authEntryPoint;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SECURE_CONFIG_PERMIT_ALL).permitAll() // Authentication işlemleri herkese açık
                        .requestMatchers("/api/profile/**").hasAnyRole("PATIENT", "DOCTOR") // Kullanıcı profilleri
                        .requestMatchers("/api/doctor/**").hasRole("DOCTOR") // Doktor erişimleri
                        .requestMatchers("/api/blood-tests/upload").hasAnyRole("PATIENT", "DOCTOR") // Tahlil yükleme
                        .requestMatchers("/api/blood-tests/{tcNumber}").hasAnyRole("PATIENT","DOCTOR")
                        //.requestMatchers("/api/doctor-analyze-for-admin/{doctorId}").hasRole("ADMIN")
                        //.requestMatchers("/api/patient-analyze-for-admin/{tcNumber}").hasRole("ADMIN")// Kullanıcı tahlilleri
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}