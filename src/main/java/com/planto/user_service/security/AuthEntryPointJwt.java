package com.planto.user_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Component that handles unauthorized access attempts in the application.
 * This class implements the {@link AuthenticationEntryPoint} interface to provide
 * a custom response for unauthorized requests.
 *
 * <p>When an unauthorized request is made, this class logs the error and sends
 * a JSON response containing the error details.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code logger} - Logger instance for logging unauthorized access attempts.</li>
 * </ul>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>{@code commence(HttpServletRequest, HttpServletResponse, AuthenticationException)} -
 *       Handles the unauthorized access by logging the error and sending a JSON response.</li>
 * </ul>
 *
 * @author Praful
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Handles unauthorized access attempts by logging the error and sending a JSON response.
     *
     * @param request The HTTP request that resulted in an {@link AuthenticationException}.
     * @param response The HTTP response to be sent to the client.
     * @param authException The exception that triggered this entry point.
     * @throws IOException If an input or output error occurs while writing the response.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}