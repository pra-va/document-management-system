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

	public PagingResponse() {
	}

	public PagingResponse(int currentPage, long totalItems, int pageSize) {
		this.currentPage = currentPage;
		this.totalItems = totalItems;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
