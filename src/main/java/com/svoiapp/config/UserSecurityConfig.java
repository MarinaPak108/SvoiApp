package com.svoiapp.config;

import com.svoiapp.exception.CustomAccessDeniedHandler;
import com.svoiapp.exception.CustomAuthenticationFailureHandler;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }


    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(encoder);
        //authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain appEndpoints(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/m/service")).hasAnyRole("MEMBER", "USER")
                        .requestMatchers(new AntPathRequestMatcher("/m/home")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/w/signin")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/m/visaExt")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/?continue")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/w/access-failed")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/w/login?loginError=true")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form)->form
                        .loginProcessingUrl("/w/login")
                        .defaultSuccessUrl("/m/home")
                        //.failureUrl("/w/login?loginError=true")
                        .failureHandler(authenticationFailureHandler())
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/w/logout")
                        .permitAll())
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }
}