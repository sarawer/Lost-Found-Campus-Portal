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

        http
                .authorizeHttpRequests((request) -> request

                        .requestMatchers(
                                "/",
                                "/css/**",
                                "/style.css",
                                "/register",
                                "/login",
                                "/verify-otp",
                                "/images/**"
                        ).permitAll()

                        .requestMatchers(
                                "/lost/add",
                                "/lost/edit/**",
                                "/lost/delete/**",
                                "/lost/save",
                                "/found/add",
                                "/found/edit/**",
                                "/found/delete/**",
                                "/found/save"
                        )
                        .hasRole("USER")

                        .requestMatchers(
                                "/lost/status/**",
                                "/found/status/**",
                                "/claims/**"
                        )
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .rememberMe(Customizer.withDefaults())

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}