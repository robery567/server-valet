package com.servervaletdev.config;

import com.servervaletdev.repository.provider.UserDetailServiceProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailServiceProvider userDetailsServiceProvider;

    public SecurityConfiguration(UserDetailServiceProvider userDetailsServiceProvider) {
        this.userDetailsServiceProvider = userDetailsServiceProvider;
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/account/login").permitAll()
                    .antMatchers("/account/register").permitAll()
                    .antMatchers(HttpMethod.POST, "/account/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/account/register").permitAll()
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
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll();
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsServiceProvider);
    }
}