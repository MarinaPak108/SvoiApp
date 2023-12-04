package com.svoiapp.config;

import com.svoiapp.exception.CustomAccessDeniedHandler;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
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
                        .requestMatchers(new AntPathRequestMatcher(("/w/home"))).hasAnyRole("USER", "MEMBER")
                        .anyRequest().permitAll()
                )
                .formLogin((form)->form
                        .loginProcessingUrl("/w/login")
                        .defaultSuccessUrl("/w_main/service")
                        .failureUrl("/w/login?loginError=true")
                        .permitAll())
                .logout((logout) -> logout.permitAll())
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }
}
