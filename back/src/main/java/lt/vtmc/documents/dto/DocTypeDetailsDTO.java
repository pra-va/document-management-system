package lt.vtmc.documents.dto;

import java.util.List;
import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.model.Group;

public class DocTypeDetailsDTO {

	private String name;

	
	private List<Group> groupsToApprove;

	
	private List<Group> groupsToCreate;

	public DocTypeDetailsDTO(DocType docType) {
		super();
		this.name = docType.getName();
		this.groupsToCreate = docType.getGroupsCreating();
		this.groupsToApprove = docType.getGroupsApproving();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getGroupsToApprove() {
		String[] groupsApproving = new String[groupsToApprove.size()];
		for (int i = 0; i < groupsToApprove.size(); i++) {
			groupsApproving[i] = groupsToApprove.get(i).getName();
		}
		return groupsApproving;
	}

	public void setGroupsToApprove(List<Group> groupsToApprove) {
		this.groupsToApprove = groupsToApprove;
	}

	public String[] getGroupsToCreate() {
		String[] groupsCreating = new String[groupsToCreate.size()];
		for (int i = 0; i < groupsToCreate.size(); i++) {
			groupsCreating[i] = groupsToCreate.get(i).getName();
		}
		return groupsCreating;
	}

	public void setGroupsToCreate(List<Group> groupsToCreate) {
		this.groupsToCreate = groupsToCreate;
	}

	
}
