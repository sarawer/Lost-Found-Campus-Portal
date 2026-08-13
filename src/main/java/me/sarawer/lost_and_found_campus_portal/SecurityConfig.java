package me.sarawer.lost_and_found_campus_portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> request
                        .requestMatchers("/css/**", "/register", "/login").permitAll()
                        .requestMatchers("/lost/edit/**", "/lost/delete/**",
                                "/found/edit/**", "/found/delete/**",
                                "/claims/**").hasRole("ADMIN")
                        .requestMatchers("/lost/add", "/found/add").hasRole("USER")
                        .anyRequest().authenticated())
                .rememberMe(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}