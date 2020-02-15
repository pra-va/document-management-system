package lt.vtmc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Security entry point for user authentication.
 * 
 * @author pra-va
 *
 */
@Component("restAuthenticationPoint")
public class SecurityEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Creates response for a user if he/she is unauthorized to do certain tasks.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		response.sendRedirect("/dvs");
	}

}
