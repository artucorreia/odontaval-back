package br.edu.cesmac.odontaval.config;

import br.edu.cesmac.odontaval.security.ApiKeyFilter;
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
import org.springframework.security.config.Customizer;
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
  private final ApiKeyFilter apiKeyFilter;

  @Profile("dev")
  @Bean
  public SecurityFilterChain filterChainDev(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
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

                    // users
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/all")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/v1/users")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/role")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/admin-reset-password")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/reactivate")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/password")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

                    // dashboard
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/stats")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/semester")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/class-averages")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

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
                    .requestMatchers(HttpMethod.PUT, "/api/v1/specialisms/{id}/reactivate")
                    .hasRole("ADMIN")

                    // evaluation
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations/all")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/v1/evaluations")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/evaluations/{id}")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/evaluations/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/evaluations/{id}/reactivate")
                    .hasRole("ADMIN")

                    // all
                    .anyRequest()
                    .hasAnyRole("ADMIN", "STUDENT", "PROFESSOR"))
        // api key filter
        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
        // jwt filter
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Profile("prod")
  @Bean
  public SecurityFilterChain filterChainProd(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
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

                    // users
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/all")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/v1/users")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/role")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/admin-reset-password")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/reactivate")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/password")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

                    // dashboard
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/stats")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/semester")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/class-averages")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

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
                    .requestMatchers(HttpMethod.PUT, "/api/v1/specialisms/{id}/reactivate")
                    .hasRole("ADMIN")

                    // evaluation
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations/all")
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/v1/evaluations")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.GET, "/api/v1/evaluations/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/evaluations/{id}")
                    .hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/evaluations/{id}")
                    .hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/evaluations/{id}/reactivate")
                    .hasRole("ADMIN")

                    // all
                    .anyRequest()
                    .hasAnyRole("ADMIN", "STUDENT", "PROFESSOR"))
        // api key filter
        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
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
