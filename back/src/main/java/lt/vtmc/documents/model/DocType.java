package lt.vtmc.documents.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lt.vtmc.groups.model.Group;

/**
 * User entity for database.
 * 
 * @author VStoncius
 *
 */
@Entity
@Table(name = "document_type")
public class DocType {
	
	@Id
	private int id;
	
	private String documentType;
	
	@ManyToMany(mappedBy = "docTypesToApprove", fetch = FetchType.EAGER)
	private List<Group> groupsApproving;
	
	@ManyToMany(mappedBy = "docTypesToCreate", fetch = FetchType.EAGER)
	private List<Group> groupsCreating;
	/**
	 * Empty constructor.
	 */
	public DocType() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public List<Group> getGroupsApproving() {
		return groupsApproving;
	}
	public void setGroupsApproving(List<Group> groupsApproving) {
		this.groupsApproving = groupsApproving;
	}
	public List<Group> getGroupsCreating() {
		return groupsCreating;
	}
	public void setGroupsCreating(List<Group> groupsCreating) {
		this.groupsCreating = groupsCreating;
	}
	public DocType(int id, String documentType) {
		super();
		this.id = id;
		this.documentType = documentType;
	}
	
	
}
