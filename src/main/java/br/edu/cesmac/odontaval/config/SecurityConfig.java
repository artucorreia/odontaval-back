package br.edu.cesmac.odontaval.config;

import br.edu.cesmac.odontaval.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final SecurityFilter securityFilter;

  @Profile("dev")
  @Bean
  public SecurityFilterChain filterChainDev(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .authorizeHttpRequests(
            authorize ->
                authorize

                    // todo: review permissions

                    // auth
                    .requestMatchers(HttpMethod.POST, "/api/auth/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/register")
                    .permitAll()

                    // specialism
                    .requestMatchers(HttpMethod.POST, "/api/v1/specialisms")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/specialisms")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/specialisms/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/specialisms/{id}")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/specialisms/{id}")
                    .hasRole("ADMIN")

                    // exam
                    .requestMatchers(HttpMethod.POST, "/api/v1/exams")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/exams")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.GET, "/api/v1/exams/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/exams/{id}")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/exams/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR")

                    // all
                    .anyRequest()
                    .hasAnyRole("ADMIN", "STUDENT", "PROFESSOR"))
        // jwt filter
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Profile("prod")
  @Bean
  public SecurityFilterChain filterChainProd(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    // auth
                    .requestMatchers(HttpMethod.POST, "/api/auth/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/register")
                    .permitAll()

                    // all
                    .anyRequest()
                    .hasAnyRole("ADMIN", "STUDENT", "PROFESSOR"))
        // jwt filter
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
