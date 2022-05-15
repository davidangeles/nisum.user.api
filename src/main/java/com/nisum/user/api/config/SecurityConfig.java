package com.nisum.user.api.config;

import com.nisum.user.api.security.JwtAuthenticationFilter;
import com.nisum.user.api.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenService tokenService;

  private final Environment environment;

  public static final String[] requestPermit = {
      "/h2-console/**",
      "/actuator/**",
      "/token"
  };

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    http.exceptionHandling()
        .authenticationEntryPoint(
            (request, response, ex) -> {
              response.setContentType("application/json");
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getOutputStream().println("{ \"mensaje\": \"" + "No autorizado" + "\" }");
            }
        )
        .and();

    http.authorizeRequests()
        .antMatchers(this.requestPermit).permitAll()
        .anyRequest().authenticated()
        .and();

    http.headers().frameOptions().disable()
        .and();

    http.addFilterBefore(new JwtAuthenticationFilter(environment, tokenService), UsernamePasswordAuthenticationFilter.class);
  }


}
