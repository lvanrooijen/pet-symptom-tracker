package com.laila.pet_symptom_tracker.temporary;

import com.laila.pet_symptom_tracker.mainconfig.TerminalColors;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.security.web.csrf.CsrfToken;

// TODO delete me, Tijdelijk om die token te bekijken.

public class CsrfTokenLogger implements Filter {
  private final Logger log = Logger.getLogger(CsrfTokenLogger.class.getName());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
    log.info(TerminalColors.setGirlyShit("CSRF Token: " + token.getToken()));
    chain.doFilter(request, response);
  }
}
