package lt.vtmc.documents.dto;

public class ApproveDocumentCommand {

	private String username;

	public ApproveDocumentCommand(String username) {
		this.username = username;
	}

	public ApproveDocumentCommand() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
