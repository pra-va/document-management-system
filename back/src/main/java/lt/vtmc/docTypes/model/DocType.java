package lt.vtmc.docTypes.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lt.vtmc.documents.model.Document;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(mappedBy = "docTypesToApprove")
	private List<Group> groupsApproving = new ArrayList<Group>();

	@ManyToMany(mappedBy = "docTypesToCreate")
	private List<Group> groupsCreating = new ArrayList<Group>();

	@OneToMany(fetch = FetchType.LAZY)
	private List<Document> documentList;

	/**
	 * Empty constructor.
	 */
	public DocType() {
	}

	public List<Document> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DocType(int id, String name, List<Group> groupsApproving, List<Group> groupsCreating) { // String
																									// documentType,
		super();
		this.id = id;
		this.name = name;
//		this.documentType = documentType;
		this.groupsApproving = groupsApproving;
		this.groupsCreating = groupsCreating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupsApproving == null) ? 0 : groupsApproving.hashCode());
		result = prime * result + ((groupsCreating == null) ? 0 : groupsCreating.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocType other = (DocType) obj;
		if (groupsApproving == null) {
			if (other.groupsApproving != null)
				return false;
		} else if (!groupsApproving.equals(other.groupsApproving))
			return false;
		if (groupsCreating == null) {
			if (other.groupsCreating != null)
				return false;
		} else if (!groupsCreating.equals(other.groupsCreating))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocType [id=" + id + ", name=" + name + ", groupsApproving=" + groupsApproving + ", groupsCreating="
				+ groupsCreating + ", documentList=" + documentList + "]";
	}

}
