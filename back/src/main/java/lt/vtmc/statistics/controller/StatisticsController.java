package lt.vtmc.statistics.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.paging.PagingData;
import lt.vtmc.statistics.service.StatService;

@RestController
public class StatisticsController {

	private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	private StatService statService;

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/statisticsdtype")
	public Map<String, Object> getDocTypeStatistics(String username, int startDate, int endDate,
			@RequestBody PagingData pagingData) {

		LOG.info("# LOG # Initiated by [{}]: Requested docType statistics #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return statService.getDocTypeStatistics(username, startDate, endDate, pagingData);

	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/statisticsuser")
	public Map<String, Object> getDocUserStatistics(String username, @RequestBody PagingData pagingData) {

		LOG.info("# LOG # Initiated by [{}]: Requested users statistics #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return (statService.getUserStatistics(username, pagingData));

	}
}
