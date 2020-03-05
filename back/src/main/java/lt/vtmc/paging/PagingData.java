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

	/**
	 * 
	 * @return search value
	 */
	public String getSearchValueString() {
		return searchValueString;
	}

	/**
	 * 
	 * @param searchValueString
	 */
	public void setSearchValueString(String searchValueString) {
		this.searchValueString = searchValueString;
	}

	/**
	 * 
	 * @return field name to sort by.
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * 
	 * @param sortBy
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * 
	 * @return order for sorting field "sortBy"
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 
	 * @param orderBy - "desc" for descending or anything else for ascending
	 */
	public void setOrder(String orderBy) {
		this.order = orderBy;
	}

	/**
	 * 
	 * @return size per page
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * set size per page
	 * 
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 
	 * @return number of page where first page is 0
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 
	 * @param page number where 0 is first page
	 */
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
