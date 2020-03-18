package lt.vtmc.statistics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.statistics.dto.StatisticsDocTypeDTO;
import lt.vtmc.statistics.dto.StatisticsUserDTO;
import lt.vtmc.statistics.service.StatService;

@RestController
public class StatisticsController {

	private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	private StatService statService;

	@GetMapping(path = "/api/statisticsdtype")
	public List<StatisticsDocTypeDTO> getDocTypeStatistics(String username, int startDate, int endDate) {

		System.out.println("****************");
		System.out.println("lt.vtmc.statistics.controller.StatisticsController.getDocTypeStatistics(String, int, int)");
		System.out.println("username: " + username);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		System.out.println("****************");

		LOG.info("# LOG # Initiated by [{}]: Requested docType statistics #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return (statService.getDocTypeStatistics(username, startDate, endDate));

	}

	@GetMapping(path = "/api/statisticsuser")
	public List<StatisticsUserDTO> getDocUserStatistics(String username) {

		LOG.info("# LOG # Initiated by [{}]: Requested users statistics #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return (statService.getUserStatistics(username));

	}
}
