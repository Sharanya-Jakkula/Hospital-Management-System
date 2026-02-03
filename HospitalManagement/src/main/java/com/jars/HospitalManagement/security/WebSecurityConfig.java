package com.jars.HospitalManagement.security;

import com.jars.HospitalManagement.entity.type.PermissionType;
import com.jars.HospitalManagement.entity.type.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthFilter jwtAuthFilter;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF for POST login/signup
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**").permitAll() // allow login/signup
                        .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority(PermissionType.APPOINTMENT_DELETE.name(),PermissionType.USER_MANAGE.name())
                        .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name()) // allow login/signup
                        .requestMatchers("/doctors/**").hasAnyRole(RoleType.DOCTOR.name(),RoleType.ADMIN.name()) // allow login/signup
                        .anyRequest().authenticated() // all other requests open
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2-> {
                    oAuth2.failureHandler(
                            (request,response,exception)->{
                                log.error("OAuth2 error : {}",exception.getMessage());
                            })
                            .successHandler(oAuth2SuccessHandler);
                })
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
                            handlerExceptionResolver.resolveException(request,response, null,exception);
                        }
                    });
                });

        return http.build();
    }


}
