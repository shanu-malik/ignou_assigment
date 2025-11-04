package com.example.assignment_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.assignment_api.core.constants.AppConstants;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final String[] PUBLIC_URLS = AppConstants.PUBLIC_ROUTES.toArray(new String[0]);
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip public URLs
        for (String url : PUBLIC_URLS) {
            if (pathMatcher.match(url, path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            System.err.println("authHeader == " + authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                sendUnauthorized(response, "Missing or invalid Authorization header");
                return;
            }

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (!jwtUtil.validateToken(token, username)) {
                sendUnauthorized(response, "Invalid or expired token");
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            sendUnauthorized(response, "Unauthorized: " + e.getMessage());
        }
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}
