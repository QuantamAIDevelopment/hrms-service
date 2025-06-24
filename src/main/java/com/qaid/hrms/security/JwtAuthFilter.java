package com.qaid.hrms.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUsersDetailsService myUsersDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        String header = request.getHeader("Authorization");

        // Step 1: Extract and validate the token
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtService.extractUserName(token); // Extract username from the token
            } catch (ExpiredJwtException e) {
                // Just log the error and continue with the filter chain
                // This will allow the request to proceed to the security filter
                // which will handle the authentication failure
                filterChain.doFilter(request, response);
                return;
            } catch (JwtException e) { // Assuming JwtException for invalid token
                // Just log the error and continue with the filter chain
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Step 2: If username is extracted and not yet authenticated, proceed with user validation
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = myUsersDetailsService.loadUserByUsername(username);
                // Step 3: Validate the token with user details
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (UsernameNotFoundException | BadCredentialsException e) {
                // Just log the error and continue with the filter chain
                // Don't throw exceptions here as they will interrupt the filter chain
            }
        }
        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}