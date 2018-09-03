package com.example.bookservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
    .and()
      .httpBasic()
    .and()
      .authorizeRequests()
        .antMatchers("/index.html", "/", "/login").permitAll()
        .anyRequest().authenticated();
  }
  
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//      http
//      .cors()
//      .and()
//      .httpBasic()
//      .and()
//        .authorizeRequests()
//          .antMatchers("/login").permitAll()
//          .anyRequest().authenticated()
//      .and()
//        .formLogin()
//          .loginPage("/login")
//          .permitAll()
//      .and()
//        .logout()
//        .permitAll();
//    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
