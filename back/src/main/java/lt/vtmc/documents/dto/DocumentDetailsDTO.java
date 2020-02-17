package lt.vtmc.documents.dto;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.model.Document;
import lt.vtmc.user.model.User;

public class DocumentDetailsDTO {
	
	private String name;

	private User author;
	
	private DocType dType;
	
	private String dateCreate;
	
	private String description;
	
	private String dateSubmit;
	
	private String dateProcessed;
	
	private String reasonToReject;
	
	private User handler;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
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

	public DocumentDetailsDTO(Document document) {
		super();
		this.name = document.getName();
		this.author = document.getAuthor();
		this.dType = document.getdType();
		this.dateCreate = document.getDateCreate();
		this.description = document.getDescription();
		this.dateSubmit = document.getDateSubmit();
		this.dateProcessed = document.getDateProcessed();
		this.reasonToReject = document.getReasonToReject();
		this.handler = document.getHandler();
	}
	
	
}
