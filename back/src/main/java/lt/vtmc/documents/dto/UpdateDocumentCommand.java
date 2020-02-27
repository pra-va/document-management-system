package lt.vtmc.documents.dto;

public class UpdateDocumentCommand {

	private String newName;
	
	private String description;
	
	private String docType;
	
	private String[] filesToRemoveUID;

	public UpdateDocumentCommand(String newName, String description, String docType, String[] filesToRemoveUID) {
		super();
		this.newName = newName;
		this.description = description;
		this.docType = docType;
		this.filesToRemoveUID = filesToRemoveUID;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
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

	public String[] getFilesToRemoveUID() {
		return filesToRemoveUID;
	}

	public void setFilesToRemoveUID(String[] filesToRemoveUID) {
		this.filesToRemoveUID = filesToRemoveUID;
	}
	
	
}