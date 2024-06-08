package com.icsd.healthcare.shared.configs;

import com.icsd.healthcare.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));

        return http
                .csrf(AbstractHttpConfigurer::disable)  // Consider enabling CSRF protection if applicable
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/login/**", "/register/**", "/", "/as","/signup/**","/valid/**","/perform_login").permitAll();
                            auth.requestMatchers("/api/slot/**", "/sessionAttributes")
                                    .hasAuthority("DOCTOR");
                            auth.anyRequest().authenticated();

                        }
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/perform_login") // Specify the URL for the login controller
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/as", true)
                        .failureUrl("/login.html?error=true")// Specify the URL of the custom login page
                        .permitAll() // Allow unauthenticated access to the login page
                )
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsServiceImpl)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .logout(logout -> logout
                      /*  .addLogoutHandler(new HeaderWriterLogoutHandler(
                                new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES) // Clear cookies

                        ))*/
                        .addLogoutHandler(clearSiteData)
                )
                .headers(headers ->
                headers.xssProtection(
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
