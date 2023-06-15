package com.kist.portal.sysconf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  @Autowired
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/**/*.{json}");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // .csrf().ignoringAntMatchers("/api/v1/**");
        .csrf().disable()
        .cors();

    http
        .authorizeRequests()
        .antMatchers("/api/v1/common/loginprocess").permitAll()
        // .antMatchers("/**").hasAnyRole("ROLE_USER")
        // .anyRequest().hasAnyRole("ROLE_USER");
        .anyRequest().permitAll();

    http
        .formLogin()
        .loginProcessingUrl("/api/v1/common/loginprocess")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler);

    // http
    // .headers().xssProtection().block(true);

    http
        .sessionManagement()
        .maximumSessions(1)
        .maxSessionsPreventsLogin(true);
  }

  // SpringSecurity 이용시 CORS 처리.
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(customAuthenticationProvider);
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

}