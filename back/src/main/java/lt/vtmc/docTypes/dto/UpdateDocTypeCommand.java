package lt.vtmc.docTypes.dto;

/**
 * DTO object requested in controller as request body and used to update
 * document types.
 * 
 *
 */
public class UpdateDocTypeCommand {

	private String newName;

	private String[] groupsApproving;

	private String[] groupsCreating;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String name) {
		this.newName = name;
	}

	public String[] getGroupsApproving() {
		return groupsApproving;
	}

	public void setGroupsApproving(String[] groupsApproving) {
		this.groupsApproving = groupsApproving;
	}

	public String[] getGroupsCreating() {
		return groupsCreating;
	}

	public void setGroupsCreating(String[] groupsCreating) {
		this.groupsCreating = groupsCreating;
	}

	public UpdateDocTypeCommand(String name, String[] groupsApproving, String[] groupsCreating) {
		super();
		this.newName = name;
		this.groupsApproving = groupsApproving;
		this.groupsCreating = groupsCreating;
	}

}
