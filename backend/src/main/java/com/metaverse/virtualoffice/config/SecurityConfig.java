// DEV ONLY — do NOT ship to production
// This configuration disables all authentication and OAuth2 login for local development convenience.
// Remove this file or gate it behind a @Profile("dev") annotation before deploying to production.
// TODO: To re-enable security, remove this file or use @Profile("dev") and configure proper authentication.

package com.metaverse.virtualoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for easier API testing in dev
            .authorizeRequests().anyRequest().permitAll() // Permit all requests
            .and()
            .oauth2Login().disable(); // Disable OAuth2 login auto-redirects
        return http.build();
    }
}
