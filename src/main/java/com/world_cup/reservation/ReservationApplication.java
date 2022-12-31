package com.world_cup.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//
//				System.out.println("CORS CONFIG");
//				registry.addMapping("/api/**")
//						.allowedOrigins("http://localhost:3000")
//						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//						.exposedHeaders("Authorization");
//			}
//		};
//	}


//	@Bean
//	public CorsConfigurationSource corsConfiguration() {
//		CorsConfiguration corsConfig = new CorsConfiguration();
//		corsConfig.applyPermitDefaultValues();
//		corsConfig.addAllowedOrigin("http://localhost:3000");
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfig);
//		return source;
//	}
}
