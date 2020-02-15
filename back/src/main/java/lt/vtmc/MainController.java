package lt.vtmc;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main controller class.
 * 
 * @author pra-va
 *
 */
@Controller
public class MainController {

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/users", "/groups", "/doctypes" })
	public ModelAndView adminView(ModelMap model) {
		return new ModelAndView("forward:/", model);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = { "/home" })
	public ModelAndView homePageView(ModelMap model) {
		// model.addAttribute("attribute", "forwardWithForwardPrefix");
		return new ModelAndView("forward:/", model);
	}

	@RequestMapping(value = { "/notFound" })
	public ModelAndView notFound(ModelMap model) {
		return new ModelAndView("forward:/notFound", model);
	}

}
