
package com.bank.bankapp.security;

import com.bank.bankapp.service.CustomUserDetailService;
import com.bank.bankapp.repository.UserRepository;
import com.bank.bankapp.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        

        // Get Authorization header
        String header = request.getHeader("Authorization");

        // Check if header is valid
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        //  Extract token
        String token = header.substring(7).trim();

        //  Extract username (email)
        String email = jwtUtil.getUsernameFromToken(token);

        //  Check if user not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //  Load user from DB
            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

            // Validate token
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
