package lt.vtmc.groups.model;

import java.util.List;

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

import lt.vtmc.documents.model.DocType;
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
	@Column(name="id")
	private int id;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="GROUPS_CREATING",joinColumns=@JoinColumn(name="group_id"), inverseJoinColumns=@JoinColumn(name="doc_id"))
	//@ElementCollection(targetClass=DocType.class)
	private List<DocType> docTypesToCreate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="GROUPS_APPROVING",joinColumns=@JoinColumn(name="group_id"), inverseJoinColumns=@JoinColumn(name="doc_id"))
	//@ElementCollection(targetClass=DocType.class)
	private List<DocType> docTypesToApprove;

	@ManyToMany(mappedBy = "groupList", fetch = FetchType.EAGER)
	private List<User> userList;
	
	

	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param docType
	 * 
	 */
	public Group(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Empty constructor.
	 */
	public Group() {
	}
	
	/**
	 * 
	 * @return getUserList
	 */
	public List<User> getUserList() {
		return userList;
	}
	/**
	 * 
	 * @param groupUserList
	 */
	public void setUserList(List<User> groupUserList) {
		this.userList = groupUserList;
	}
	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return docTypesToCreate
	 */
	public List<DocType> getDocTypesToCreate() {
		return docTypesToCreate;
	}
	/**
	 * 
	 * @param docTypesToCreate
	 */
	public void setDocTypesToCreate(List<DocType> docTypesToCreate) {
		this.docTypesToCreate = docTypesToCreate;
	}
	/**
	 * 
	 * @return docTypesToApprove
	 */
	public List<DocType> getDocTypesToApprove() {
		return docTypesToApprove;
	}
	/**
	 * 
	 * @param docTypesToApprove
	 */
	public void setDocTypesToApprove(List<DocType> docTypesToApprove) {
		this.docTypesToApprove = docTypesToApprove;
	}
}
