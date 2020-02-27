package lt.vtmc.documents.dto;

public class DocumentRejection {

	private String username;
	
	private String reasonToReject;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReasonToReject() {
		return reasonToReject;
	}

	public void setReasonToReject(String reasonToReject) {
		this.reasonToReject = reasonToReject;
	}

	public DocumentRejection(String username, String reasonToReject) {
		this.username = username;
		this.reasonToReject = reasonToReject;
	}
	
	
}
