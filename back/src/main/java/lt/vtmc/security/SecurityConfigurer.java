package lt.vtmc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import lt.vtmc.user.service.UserService;

/**
 * Web security configurer responsible for Authentication and Authorization.
 * 
 * @author pra-va
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfigurer.class);

	@Autowired
	private SecurityEntryPoint securityEntryPoint;

	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	/**
	 * This method authenticates users using provided service.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	/**
	 * !!! IMPORTANT !!! Remove /createadmint from permitall authorization at the
	 * end. This method authorizes user to access certain urls in a server. It also
	 * configures security options.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().authorizeRequests().antMatchers("/", "/swagger-ui.html", "/createadmin", "/createuser")
				.permitAll().and().formLogin().successHandler(new SimpleUrlAuthenticationSuccessHandler() {

					/**
					 * This method is responsible for creating headers and data with username that
					 * will be sent to UI.
					 */
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {

						LOG.info("# LOG # User [{}] logged in to the system #",
								SecurityContextHolder.getContext().getAuthentication().getName());

						response.setHeader("Access-Control-Allow-Credentials", "true");
						response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
						response.setHeader("Content-Type", "application/json;charset=UTF-8");
						response.getWriter().print("{\"su\": \"" + userService
								.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
								.getRole().equals("ADMIN") + "\"}");
					}

				}).failureHandler(new SimpleUrlAuthenticationFailureHandler() {

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {

						LOG.info("# LOG # Unsuccessfull login with user name: [{}] #",
								request.getParameter("username"));

						super.onAuthenticationFailure(request, response, exception);

					}

				}).loginPage("/api/login").permitAll().and().logout().logoutUrl("/api/logout")
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK) {

					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException {

						LOG.info("# LOG # user logged out #");

						super.onLogoutSuccess(request, response, authentication);
					}

				}).permitAll().and().csrf().disable().exceptionHandling().authenticationEntryPoint(securityEntryPoint)
				.and().headers().frameOptions().disable();
	}

}
