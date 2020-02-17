package lt.vtmc.documents.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.user.model.User;

@Entity
@Table(name = "Documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	private String name;

	@ManyToOne
	private User author;
	
	@ManyToOne
	private DocType dType;
	
	@NotEmpty
	private String dateCreate;
	
	@NotEmpty
	private String description;
	
	private String dateSubmit = null;
	
	private String dateProcessed = null;
	
	private String reasonToReject = null;
	
	@ManyToOne
	private User handler = null;
	
//	private String file; //placeholder
	
	
	public Document() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public DocType getdType() {
		return dType;
	}

	public void setdType(DocType dType) {
		this.dType = dType;
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

	public String getDateSubmit() {
		return dateSubmit;
	}

	public void setDateSubmit(String dateSubmit) {
		this.dateSubmit = dateSubmit;
	}

	public String getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(String dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getReasonToReject() {
		return reasonToReject;
	}

	public void setReasonToReject(String reasonToReject) {
		this.reasonToReject = reasonToReject;
	}

	public User getHandler() {
		return handler;
	}

	public void setHandler(User handler) {
		this.handler = handler;
	}

	public Document(String dateCreate, User author, DocType dType, String name, String description) {
		super();
		this.dateCreate = dateCreate;
		this.author = author;
		this.dType = dType;
		this.name = name;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((dType == null) ? 0 : dType.hashCode());
		result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result + ((dateProcessed == null) ? 0 : dateProcessed.hashCode());
		result = prime * result + ((dateSubmit == null) ? 0 : dateSubmit.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((handler == null) ? 0 : handler.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reasonToReject == null) ? 0 : reasonToReject.hashCode());
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
		Document other = (Document) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (dType == null) {
			if (other.dType != null)
				return false;
		} else if (!dType.equals(other.dType))
			return false;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateProcessed == null) {
			if (other.dateProcessed != null)
				return false;
		} else if (!dateProcessed.equals(other.dateProcessed))
			return false;
		if (dateSubmit == null) {
			if (other.dateSubmit != null)
				return false;
		} else if (!dateSubmit.equals(other.dateSubmit))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (handler == null) {
			if (other.handler != null)
				return false;
		} else if (!handler.equals(other.handler))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reasonToReject == null) {
			if (other.reasonToReject != null)
				return false;
		} else if (!reasonToReject.equals(other.reasonToReject))
			return false;
		return true;
	}
	
	
}
