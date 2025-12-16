package com.iss.securities;

import com.iss.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{

    private final JwtFilter jwtFilter;
    private final MyUserDetailService myUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                //AUTH
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/profile-pictures/**").permitAll()
                                .requestMatchers("/sweet-images/**").permitAll()
                                .requestMatchers("/actuator/health").permitAll()
                                .requestMatchers("/actuator/**").permitAll() // optional
                                //SWEETS (ADMIN)
                                .requestMatchers(HttpMethod.POST, "/api/sweets/add").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/sweets/update/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/sweets/delete/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/sweets/*/restock").hasRole("ADMIN")
                                //SWEETS (USER + ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/sweets/getAll").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/sweets/search").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/sweets/*/purchase").authenticated()
                                //EVERYTHING ELSE
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, exx) -> res.sendError(401, "Unauthorized")))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception
    {
        AuthenticationManagerBuilder amb = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        amb.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
        return amb.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
