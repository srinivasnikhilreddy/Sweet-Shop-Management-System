package com.iss.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.iss.securities.JwtUtil;
import com.iss.securities.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*JWT filter that validates access tokens and sets Authentication in the SecurityContext.
- Skips public endpoints (like /api/auth/** and /profile-pictures/**)
- Accepts Authorization: Bearer <token>*/
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter
{
    private final JwtUtil jwtUtil;
    private final MyUserDetailService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest)
    {
        return httpServletRequest.getServletPath().startsWith("/api/auth")
                || HttpMethod.OPTIONS.matches(httpServletRequest.getMethod());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws IOException, ServletException
    {
        String header = httpServletRequest.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try{
                String username = jwtUtil.validateTokenAndRetrieveSubject(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch(JWTVerificationException ex){
                httpServletResponse.sendError(401, "Invalid JWT");
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
