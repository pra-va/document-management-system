package lt.vtmc.statistics.dto;

public class StatisticsDocTypeDTO {
	
	private String docType;
	private int numberOfSubmitted;
	private int numberOfAccepted;
	private int numberOfRejected;
	
	
	public StatisticsDocTypeDTO() {
		
	}


	public String getDocType() {
		return docType;
	}


	public void setDocType(String docType) {
		this.docType = docType;
	}


	public int getNumberOfSubmitted() {
		return numberOfSubmitted;
	}


	public void setNumberOfSubmitted(int numberOfSubmitted) {
		this.numberOfSubmitted = numberOfSubmitted;
	}


	public int getNumberOfAccepted() {
		return numberOfAccepted;
	}


	public void setNumberOfAccepted(int numberOfAccepted) {
		this.numberOfAccepted = numberOfAccepted;
	}


	public int getNumberOfRejected() {
		return numberOfRejected;
	}


	public void setNumberOfRejected(int numberOfDeclined) {
		this.numberOfRejected = numberOfDeclined;
	}
	
	
	

}
