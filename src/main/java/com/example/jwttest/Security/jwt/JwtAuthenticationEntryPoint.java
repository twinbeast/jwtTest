package com.example.jwttest.Security.jwt;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {

        log.error("Responding with unauthorized error. Message - {}", e.getMessage());

        ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute("unauthorization");

        if(unAuthorizationCode==null){
            log.error(request.getRequestURI()+" - unAuthorizationCode Null");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorization");
        }else{
            request.setAttribute("response.failure.code", unAuthorizationCode.name());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode.message());
        }

    }

}