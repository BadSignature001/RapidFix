package com.spring.rapidfix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.spring.rapidfix.filter.JwtAuthenticationFilter;

import com.spring.rapidfix.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	

	private final UserDetailsServiceImp userDetailsServiceImp;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationFilter jwtAuthenticationFilter2;

	private final CustomLogoutHandler logoutHandler;

	public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter,
			CustomLogoutHandler logoutHandler ,JwtAuthenticationFilter jwtAuthenticationFilter2) {
		this.userDetailsServiceImp = userDetailsServiceImp;

		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtAuthenticationFilter2 = jwtAuthenticationFilter2 ;

		this.logoutHandler = logoutHandler;

	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers("/endpoint/**").permitAll().anyRequest().authenticated())
				.userDetailsService(userDetailsServiceImp)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter2, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(e -> e
						.accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(403))
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.logout(l -> l.logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(
						(request, response, authentication) -> SecurityContextHolder.clearContext()))
				.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
	  
}

//s
