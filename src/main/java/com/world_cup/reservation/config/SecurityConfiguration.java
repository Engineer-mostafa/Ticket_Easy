package com.world_cup.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception{
//        System.out.println("HI");
//
//        // disable cross site request forgery
//        http.csrf().disable();
//        //add cors filter
////        http.cors(cors -> cors.disable());
//
//
//
//        // build design pattern
//        return http.build();
//    }
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//
//    http
//            .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .anyRequest().authenticated();
//        http.cors();
//        http.csrf().disable();
//}

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST" , "PUT" , "DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
