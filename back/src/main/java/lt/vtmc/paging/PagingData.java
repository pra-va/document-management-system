package lt.vtmc.paging;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class used as a request body to form Pageable object for pagination and table
 * data search by name.
 * 
 * @author pra-va
 *
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PagingData {
	private int limit;
	private int page;
	private String sortBy;
	private String order;
	private String searchValueString = null;

	/**
	 * Constructor sets standard project-wide pagination which is: 8 items per
	 * single page. It also sets default page which is 0 (number 1).
	 */
	public PagingData() {
		this.limit = 8;
		this.page = 0;
	}

	public String getSearchValueString() {
		return searchValueString;
	}

	public void setSearchValueString(String searchValueString) {
		this.searchValueString = searchValueString;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String orderBy) {
		this.order = orderBy;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 
	 * @return Pageable object with data set by request from ui. Returned object
	 *         should be used in JPA (JPQL) queries to get data in page format.
	 */
	@JsonIgnore
	public Pageable getPageable() {
		if (sortBy != null & order != null) {
			if (order.equals("desc")) {
				return PageRequest.of(page, limit, Sort.by(this.sortBy).descending());
			} else {
				return PageRequest.of(page, limit, Sort.by(this.sortBy).ascending());
			}
		} else {
			return PageRequest.of(page, limit);
		}
	}

}
