package org.ist.ur.org.auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    if (e.getClass().getSimpleName().equals("UsernameNotFoundException")) {
      logger.error(e.getClass().getSimpleName() + " " + e.getMessage());
      response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    } else {
      logger.error("Jesus Unauthorized error. Message - {}", e.getMessage());
      logger.info(e.getClass().getSimpleName());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
    }
  }
}
