package com.jars.HospitalManagement.security;

import com.jars.HospitalManagement.entity.User;
import com.jars.HospitalManagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final AuthUtil authUtil;

    private final HandlerExceptionResolver handlerExceptionResolver;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            log.info("Incoming request : {}", request.getRequestURI());
//            final String requestTokenHeader = request.getHeader("Authorization");
//            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//            }
//
//            String token = requestTokenHeader.split("Bearer ")[1];
//            String username = authUtil.getUsernameFromToken(token);
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                User user = userRepository.findByUsername(username).orElseThrow();
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//            }
//            filterChain.doFilter(request, response);
//        }
//        catch (Exception ex){
//            handlerExceptionResolver.resolveException(request,response,null,ex);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            log.info("Incoming request : {}", request.getRequestURI());

            String authHeader = request.getHeader("Authorization");

            // ✅ If no JWT, just continue the chain and STOP
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return; // ⭐ VERY IMPORTANT
            }

            String token = authHeader.substring(7); // removes "Bearer "
            String username = authUtil.getUsernameFromToken(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository.findByUsername(username).orElseThrow();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            // ❌ DO NOT write to response again
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/oauth2")
                || path.startsWith("/login/oauth2")
                || path.startsWith("/auth");
    }


}
