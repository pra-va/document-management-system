package lt.vtmc.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lt.vtmc.paging.PagingData;
import lt.vtmc.paging.PagingResponse;
import lt.vtmc.statistics.dto.StatisticsDocTypeDTO;
import lt.vtmc.statistics.dto.StatisticsUserDTO;
import lt.vtmc.user.dao.UserRepository;

/**
 * Statistics Service with methods for Document type statistics or User
 * statistics
 * 
 * @author LD
 *
 */
@Service
public class StatService {

	@Autowired
	private UserRepository userRepository;

	public Map<String, Object> getDocTypeStatistics(String username, int startDate, int endDate,
			PagingData pagingData) {

		if (startDate == -1 || endDate == -1) {

			Page<StatisticsDocTypeDTO> docStatisticsDto = userRepository.statisticsByDocType(username,
					pagingData.getSearchValueString(), pagingData.getPageable());

			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("pagingData", new PagingResponse(docStatisticsDto.getNumber(),
					docStatisticsDto.getTotalElements(), docStatisticsDto.getSize()));
			responseMap.put("statistics", docStatisticsDto.getContent());

			return responseMap;
		}

		String start = convertDate(startDate + 1).toInstant().toString();
		String end = convertDate(endDate + 1).toInstant().toString();

		if (start == null || end == null) {
			return null;
		}

		Page<StatisticsDocTypeDTO> docStatisticsDto = userRepository.statisticsByDocType(username,
				pagingData.getSearchValueString(), start, end, pagingData.getPageable());

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData", new PagingResponse(docStatisticsDto.getNumber(),
				docStatisticsDto.getTotalElements(), docStatisticsDto.getSize()));
		responseMap.put("statistics", docStatisticsDto.getContent());

		return responseMap;
	}

	public Date convertDate(int date) {
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(date + "");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Object> getUserStatistics(String username, PagingData pagingData) {

		Page<StatisticsUserDTO> statisticsData = userRepository.statisticsByUsers(username,
				pagingData.getSearchValueString(), pagingData.getPageable());
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData", new PagingResponse(statisticsData.getNumber(), statisticsData.getTotalElements(),
				statisticsData.getSize()));
		responseMap.put("statistics", statisticsData.getContent());
		return responseMap;

	}

}
