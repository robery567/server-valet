package com.servervaletdev.config;

import io.netty.handler.codec.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/account/**").permitAll()
                    //.antMatchers(HttpMethod.POST, "/account/**").permitAll()
                    .antMatchers("/ws/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/account/login")
                    .loginProcessingUrl("/j_spring_security_check")
                    .failureUrl("/account/login?error")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/account/login?logout")
                    .permitAll();
        }

    }
}