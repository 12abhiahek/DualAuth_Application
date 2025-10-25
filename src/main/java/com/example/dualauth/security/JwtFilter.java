package com.example.dualauth.security;

import com.example.dualauth.service.JwtProviderService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProviderService jwtProviderService;

    public JwtFilter(JwtProviderService jwtProviderService) {
        this.jwtProviderService = jwtProviderService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtProviderService.parseToken(token);
                String authType = claims.get("auth_type", String.class);
                String subject = claims.getSubject();

                if ("EMAIL".equalsIgnoreCase(authType)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            subject, null, List.of(new SimpleGrantedAuthority("AUTH_EMAIL"))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else if ("MOBILE".equalsIgnoreCase(authType)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            subject, null, List.of(new SimpleGrantedAuthority("AUTH_MOBILE"))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {

                // invalid token -> leave security context empty


//                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.getWriter().write("Missing or invalid Authorization header");
//                    return;
//                }

            }

        }

        filterChain.doFilter(request, response);
    }
}
