package com.greenlend.greenlend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenlend.greenlend.dto.UserDTO;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.service.GLUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final GLUserServiceImpl userService;

    @Value("${cors.origin}")
    private String corsOrigin;

    @Value("${cors.domain}")
    private String corsDomain;

    public WebSecurityConfig(@Lazy UserDetailsServiceImpl userDetailsService, @Lazy GLUserServiceImpl userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        return config;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(this::getCorsConfiguration)
                .and()
//                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers("/**").hasAnyRole("ADMIN", "USER", "ANONYMOUS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler((req, res, auth) -> {
                    CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
                    res.setCharacterEncoding("UTF-8");
                    GLUser user = userService.findUserById(customUserDetails.getId());
                    ObjectMapper om = new ObjectMapper();
                    String json = om.writeValueAsString(new UserDTO(user));
                    res.getWriter().append(json);
                    res.setStatus(HttpServletResponse.SC_ACCEPTED);
                    res.setHeader("Access-Control-Allow-Credentials", "true");
                    res.setHeader("Access-Control-Allow-Origin", corsOrigin);
                    res.setHeader("Set-Cookie", "JSESSIONID=" + req.getRequestedSessionId());
                    res.setHeader("Content-Type", "application/json");
                })
                .failureHandler((req, res, exp) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setHeader("Access-Control-Allow-Credentials", "true");
                    res.setHeader("Access-Control-Allow-Origin", corsOrigin);
                    res.setHeader("Access-Control-Allow-Methods", "GET, POST");
                    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Set-Cookie, *");
                })
//                    .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}