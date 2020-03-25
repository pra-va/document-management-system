package lt.vtmc.groups.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.user.model.User;

/**
 * User entity for database.
 * 
 * @author VStoncius
 *
 */
@Entity
@Table(name = "Groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotEmpty
	private String name;

	@Column(name = "description", length = 500)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "GROUPS_CREATING", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "doc_id"))
	private List<DocType> docTypesToCreate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "GROUPS_APPROVING", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "doc_id"))
	private List<DocType> docTypesToApprove;

	@ManyToMany(mappedBy = "groupList", fetch = FetchType.EAGER)
	private List<User> userList;

	public Group(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Group() {
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> groupUserList) {
		this.userList = groupUserList;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DocType> getDocTypesToCreate() {
		return docTypesToCreate;
	}

	public void setDocTypesToCreate(List<DocType> docTypesToCreate) {
		this.docTypesToCreate = docTypesToCreate;
	}

	public List<DocType> getDocTypesToApprove() {
		return docTypesToApprove;
	}

	public void setDocTypesToApprove(List<DocType> docTypesToApprove) {
		this.docTypesToApprove = docTypesToApprove;
	}
}
