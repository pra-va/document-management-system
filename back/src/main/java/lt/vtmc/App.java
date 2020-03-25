package lt.vtmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main runner class that is used to run applicaiotn.
 * 
 * @author pra-va
 *
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {

	/**
	 * Main application method.
	 * 
	 * @param args for application
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * Spring application configuration.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

}
