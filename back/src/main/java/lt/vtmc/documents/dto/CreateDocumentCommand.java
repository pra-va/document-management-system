package lt.vtmc.documents.dto;

public class CreateDocumentCommand {

	private String name;
	
	private String authorUsername;
	
	private String description;
	
	private String docType;
	
//	private String currentTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

//	public String getCurrentTime() {
//		return currentTime;
//	}
//
//	public void setCurrentTime(String currentTime) {
//		this.currentTime = currentTime;
//	}

	public CreateDocumentCommand(String name, String authorUsername, String description, String docType) { // , String currentTime
		super();
		this.name = name;
		this.authorUsername = authorUsername;
		this.description = description;
		this.docType = docType;
//		this.currentTime = currentTime;
	}
	
	
}
