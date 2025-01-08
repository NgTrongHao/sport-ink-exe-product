package rubberduck.org.sportinksystem.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rubberduck.org.sportinksystem.core.applicationservice.token.TokenProvider;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(TokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = extractBearerToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void setAuthentication(String token) {
        String username = jwtTokenProvider.extractUsername(token);
        List<String> roles = jwtTokenProvider.getAuthoritiesFromToken(token);
        var grantedAuthorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities)
        );
    }
}
