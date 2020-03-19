package lt.vtmc.statistics.dto;

public class StatisticsDocTypeDTO {

	private String docType;
	private long numberOfSubmitted;
	private long numberOfAccepted;
	private long numberOfRejected;

	public StatisticsDocTypeDTO() {

	}

	public StatisticsDocTypeDTO(String docType, long numberOfSubmitted, long numberOfAccepted, long numberOfRejected) {
		this.docType = docType;
		this.numberOfSubmitted = numberOfSubmitted;
		this.numberOfAccepted = numberOfAccepted;
		this.numberOfRejected = numberOfRejected;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public long getNumberOfSubmitted() {
		return numberOfSubmitted;
	}

	public void setNumberOfSubmitted(long numberOfSubmitted) {
		this.numberOfSubmitted = numberOfSubmitted;
	}

	public long getNumberOfAccepted() {
		return numberOfAccepted;
	}

	public void setNumberOfAccepted(long numberOfAccepted) {
		this.numberOfAccepted = numberOfAccepted;
	}

	public long getNumberOfRejected() {
		return numberOfRejected;
	}

	public void setNumberOfRejected(long numberOfDeclined) {
		this.numberOfRejected = numberOfDeclined;
	}

}
