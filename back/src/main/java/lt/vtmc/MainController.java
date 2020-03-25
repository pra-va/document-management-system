package lt.vtmc;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main controller class that is responsible for securing front end URLs.
 * 
 * @author pra-va
 *
 */
@Controller
public class MainController {

	/**
	 * URLs that can only be reached by users with administrator role.
	 * 
	 * @param model
	 * @return ModelAndView
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/users", "/groups", "/doctypes", "/admin" })
	public ModelAndView adminView(ModelMap model) {
		return new ModelAndView("forward:/", model);
	}

	/**
	 * URLs that can only be reached by users with administrator or user role.
	 * 
	 * @param model
	 * @return ModelAndView
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = { "/home", "/document", "/documents", "/sign", "/statistics" })
	public ModelAndView homePageView(ModelMap model) {
		return new ModelAndView("forward:/", model);
	}

	/**
	 * URLs that can be reached by anyone.
	 * 
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping(value = { "/notfound" })
	public ModelAndView notFound(ModelMap model) {
		return new ModelAndView("forward:/", model);
	}

}
