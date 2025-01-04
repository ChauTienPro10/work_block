package com.work_block.main.configuration;

import com.work_block.main.custom.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] PUBLIC_ENDPOINTS = {"/user/**", "/auth/**"};
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService userDetailsService;

    public JWTAuthenticationFilter(JwtTokenUtil jwtTokenUtil, @Lazy CustomUserDetailService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * filter
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                System.out.println("JWT validation error: " + e.getMessage());
            }
        }

        if(!isPublicEndpoint(request.getRequestURI()) && !jwtTokenUtil.validateToken(token)){
            throw new BadCredentialsException("Invalid or expired JWT token");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Kiểm tra các enpoint được public
     * @param requestUri
     * @return
     */
    private boolean isPublicEndpoint(String requestUri) {
        for (String publicUrl : PUBLIC_ENDPOINTS) {
            if (requestUri.matches(publicUrl.replace("**", ".*"))) {
                return true;
            }
        }
        return false;
    }
}
