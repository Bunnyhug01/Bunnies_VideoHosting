package com.example.video.security;

import com.example.video.entity.User;
import com.example.video.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    public static final String HEADER_PREFIX = "Bearer ";

    private final JwtTokenProvider provider;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) req);
//        log.info("Extracting token from HttpServletRequest: {}", token);
        if (token != null && provider.validateToken(token)) {
            User user = userDetailsService.findById(provider.getId(token));
            var auth = new UserAuthenticationToken(user);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}