package lt.vtmc.paging;

/**
 * Paging information to be attached with data that uses paging functionality.
 * 
 * @author pra-va
 *
 */
public class PagingResponse {
	private int currentPage;
	private long totalItems;
	private int pageSize;

	/**
	 * Empty constructor.
	 */
	public PagingResponse() {
	}

	/**
	 * Parameterized constructor to create paging info object.
	 * 
	 * @param currentPage
	 * @param totalItems
	 * @param pageSize
	 */
	public PagingResponse(int currentPage, long totalItems, int pageSize) {
		this.currentPage = currentPage;
		this.totalItems = totalItems;
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @return current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 
	 * @return number of data items in total
	 */
	public long getTotalItems() {
		return totalItems;
	}

	/**
	 * 
	 * @param totalItems
	 */
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * 
	 * @return page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
