package lt.vtmc.docTypes.dto;

/**
 * DTO to create document type by requesting for it as request body in
 * controller.
 * 
 *
 */
public class CreateDocTypeCommand {

	private String name;

	private String[] approving;

	private String[] creating;

	public CreateDocTypeCommand(String name, String[] approving, String[] creating) {
		super();
		this.name = name;
		this.approving = approving;
		this.creating = creating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
