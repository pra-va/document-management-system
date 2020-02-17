package lt.vtmc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This method is responsible for redirecting browser to error page in case of
 * white label error would occur. Note that white label page is disabled in
 * application.properties file.
 * 
 * @author pra-va
 *
 */
@Controller
public class Errors implements ErrorController {

	/**
	 * This method will trigger redirect to error page in case of /error white label
	 * page would occur. The redirect end point of this url is handled by react
	 * /dvs/notfound. Please check index.js in front end application for further
	 * info.
	 * 
	 * @param attributes
	 * @return
	 */
	@RequestMapping("/error")
	public RedirectView handleError(RedirectAttributes attributes) {
		return new RedirectView("/dvs/notfound");
	}

	/**
	 * This method sets error path which is triggered in case of something would go
	 * terribly wrong.
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
