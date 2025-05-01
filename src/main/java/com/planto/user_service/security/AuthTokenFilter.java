package com.planto.user_service.security;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    /**
     * Filter that processes incoming requests to validate and authenticate JWT tokens.
     * This class extends {@link OncePerRequestFilter} to ensure the filter is executed once per request.
     *
     * <p>Responsibilities:</p>
     * <ul>
     *   <li>Extracts the JWT token from the Authorization header.</li>
     *   <li>Validates the token using {@link JwtUtils}.</li>
     *   <li>Loads user details and sets the authentication in the {@link SecurityContextHolder}.</li>
     * </ul>
     *
     * <p>Fields:</p>
     * <ul>
     *   <li>{@code jwtUtils} - Utility class for handling JWT operations.</li>
     *   <li>{@code userDetailsService} - Service for loading user details by username.</li>
     * </ul>
     *
     * <p>Methods:</p>
     * <ul>
     *   <li>{@code doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)} -
     *       Processes the request to authenticate the user based on the JWT token.</li>
     *   <li>{@code parseJwt(HttpServletRequest)} - Extracts the JWT token from the Authorization header.</li>
     * </ul>
     *
     * @author Praful
     */
    @Component
    public class AuthTokenFilter extends OncePerRequestFilter {

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserDetailsService userDetailsService;

        private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

        /**
         * Processes the incoming request to validate and authenticate the JWT token.
         *
         * @param request The HTTP request.
         * @param response The HTTP response.
         * @param filterChain The filter chain to pass the request and response to the next filter.
         * @throws ServletException If a servlet-specific error occurs.
         * @throws IOException If an input or output error occurs.
         */
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            try {
                String jwt = parseJwt(request);
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.debug("User authenticated: " + SecurityContextHolder.getContext().getAuthentication());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }

            filterChain.doFilter(request, response);
        }

        /**
         * Extracts the JWT token from the Authorization header of the HTTP request.
         *
         * @param request The HTTP request.
         * @return The JWT token if present and valid, otherwise null.
         */
        private String parseJwt(HttpServletRequest request) {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return null;
        }
    }