package com.gmail.vladbaransky.webmodule.config;

import com.gmail.vladbaransky.servicemodule.config.PasswordEncoderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.gmail.vladbaransky.repositorymodule.model.RoleEnum.*;
import static com.gmail.vladbaransky.repositorymodule.model.RoleEnum.SECURE_API_USER;

@Configuration
@Profile("!test")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoderConfig passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoderConfig passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new UserAccessDeniedHandler();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/users/**", "/reviews")
                .hasAnyRole(ADMINISTRATION.name())
                .antMatchers("/articles", "/articles/delete", "/items")
                .hasAnyRole(CUSTOMER.name(), SALE_USER.name())
                .antMatchers(HttpMethod.POST, "/items")
                .hasRole(CUSTOMER.name())
                .antMatchers("/reviews/add", "/reviews")
                .hasAnyRole(CUSTOMER.name())
                .antMatchers(HttpMethod.POST, "/orders", "/reviews/add")
                .hasAnyRole(CUSTOMER.name())
                .antMatchers("/articles/**", "/items/**", "/orders/**")
                .hasAnyRole(SALE_USER.name())
                .antMatchers("/api/**")
                .hasAnyRole(SECURE_API_USER.name())
                .antMatchers("login")
                .permitAll()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf().disable();
    }
}
