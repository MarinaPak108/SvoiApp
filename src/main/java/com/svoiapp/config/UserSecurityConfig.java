package com.svoiapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

    private UserDetailsService uds;

    private PasswordEncoder encoder;

    public UserSecurityConfig(UserDetailsService uds, BCryptPasswordEncoder encoder) {
        this.uds = uds;
        this.encoder = encoder;
    }


    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain appEndpoints(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/resources/",   "/login.html",
                                "/static/", "/js/", "/css/", "/img/", "/json/").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin((form)->form
                        .loginProcessingUrl("/w/login")
                        .defaultSuccessUrl("/w_main/service")
                        .loginPage("/login.html").
                        permitAll())
                .logout((logout) -> logout.permitAll());
        System.out.println("check");
        return http.build();
    }
}
