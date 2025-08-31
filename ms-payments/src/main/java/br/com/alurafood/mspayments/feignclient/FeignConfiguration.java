package br.com.alurafood.mspayments.feignclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class FeignConfiguration implements RequestInterceptor {
    private Jwt jwt;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            var tokenValue = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
            requestTemplate.header(AUTHORIZATION, tokenValue);
        }
    }
}
