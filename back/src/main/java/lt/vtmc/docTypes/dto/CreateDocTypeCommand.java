package lt.vtmc.docTypes.dto;

public class CreateDocTypeCommand {

	private String name;
	
//	private String documentType;
	
	private String[] approving;
	
	private String[] creating;


	public CreateDocTypeCommand(String name, String[] approving, String[] creating) { //String documentType, 
		super();
		this.name = name;
//		this.documentType = documentType;
		this.approving = approving;
		this.creating = creating;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getDocumentType() {
//		return documentType;
//	}
//
//	public void setDocumentType(String documentType) {
//		this.documentType = documentType;
//	}

	public String[] getApproving() {
		return approving;
	}

	public void setApproving(String[] approving) {
		this.approving = approving;
	}

	public String[] getCreating() {
		return creating;
	}

	public void setCreating(String[] creating) {
		this.creating = creating;
	}

	
	
}
