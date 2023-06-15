package com.kist.portal.sysconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

import com.kist.portal.sysconf.filter.SimpleCorsFilter;

/**
 * Spring Security Login 방식 변경으로 인한 사용안함 처리
 */
// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    SimpleCorsFilter simpleCorsFilter;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring()
//		   .antMatchers(HttpMethod.OPTIONS, "/**/*")
		   .antMatchers("/**/*.{js, html, css, json, ico}")
		;
	} 

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
	    	.addFilterBefore(simpleCorsFilter, CsrfFilter.class)
	        .csrf()
	          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	          .ignoringAntMatchers("/api/v1/**")
	        .and()
	          .authorizeRequests()
	          .anyRequest().permitAll()                      // 접근을 전부 허용
//	          .antMatchers("/").permitAll()
	        .and()
	          .headers().xssProtection().block(true)
	        .and()
	          .frameOptions()
	          .disable()
	          .httpStrictTransportSecurity()
	              .includeSubDomains(true)
	              .maxAgeInSeconds(31536)
	    ;	
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}password")
            .roles("USER");
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
