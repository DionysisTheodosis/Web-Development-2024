package com.icsd.healthcare.shared.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig{

    private final UserAuthenticationProvider authenticationProvider;
    private final CustomCorsConfiguration customCorsConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        http.headers(headers ->
                headers.xssProtection(
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        cps -> cps.policyDirectives("script-src 'self'")
                ));
        http.csrf(AbstractHttpConfigurer::disable).cors(c -> c.configurationSource(customCorsConfiguration));

        http.authorizeHttpRequests(
                request -> {
                    request.requestMatchers( "/api/v1/auth/register/**", "/", "/signup/**","/api/v1/auth/isSignIn" ,"/api/v1/auth/user-role","/api/v1/auth/valid/doctor", "/api/v1/auth/login","/api/v1/auth/user/roles",
                            "/v3/api-docs","/v3/api-docs/**","/c","/swagger-ui/**","/api/v1/session-cookie").permitAll();
                    request.requestMatchers("api/v1/medical-history-records/**","/api/v1/patient","/api/v1/auth/name","/api/v1/auth/logout","/slot","/api/v1/appointments/**").hasAnyAuthority("PATIENT","DOCTOR","SECRETARY");
                    request.requestMatchers("/**").hasAuthority("DOCTOR");
                    request.anyRequest().authenticated();
                }
        );

        http.securityContext(context -> context.securityContextRepository(securityContextRepository));

        http.sessionManagement(session -> {
                    session.maximumSessions(1).maxSessionsPreventsLogin(true);
                    session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession);
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                }
        );

        http.logout(logout -> {
                    logout.logoutUrl("/api/v1/auth/logout");
                    logout.addLogoutHandler(
                            new HeaderWriterLogoutHandler(
                                    new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)
                            )
                    );
                    logout.deleteCookies("SESSION");
                }
        );

        http.authenticationProvider(authenticationProvider);

        return http.build();
    }


}