package rubberduck.org.sportinksystem.infrastructure.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApplicationAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LogManager.getLogger(ApplicationAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.debug(authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}

