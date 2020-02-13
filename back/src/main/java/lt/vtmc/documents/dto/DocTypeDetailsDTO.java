package lt.vtmc.documents.dto;

import java.util.List;

import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.model.Group;

public class DocTypeDetailsDTO {

	private String name;

	private List<Group> approving;

	private List<Group> creating;

	public DocTypeDetailsDTO(DocType docType) {
		super();
		this.name = docType.getName();
		this.creating = docType.getGroupsCreating();
		this.approving = docType.getGroupsApproving();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getApproving() {
		String[] groupsApproving = new String[approving.size()];
		for (int i = 0; i < approving.size(); i++) {
			groupsApproving[i] = approving.get(i).getName();
		}
		return groupsApproving;
	}

	public void setGroupsToApprove(List<Group> groupsToApprove) {
		this.approving = groupsToApprove;
	}

	public String[] getCreating() {
		String[] groupsCreating = new String[creating.size()];
		for (int i = 0; i < creating.size(); i++) {
			groupsCreating[i] = creating.get(i).getName();
		}
		return groupsCreating;
	}

	public void setGroupsToCreate(List<Group> groupsToCreate) {
		this.creating = groupsToCreate;
	}

}
