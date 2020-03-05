package lt.vtmc.documents.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.Status;
import lt.vtmc.documents.model.Document;
import lt.vtmc.files.model.File4DB;
import lt.vtmc.user.model.User;

public class DocumentDetailsDTO {

	private String name;

	private String author;

	private String processedBy;

	@JsonIgnore
	private DocType dType;

	private String type;

	private String dateCreate;

	private String description;

	private String dateSubmit;

	private String dateProcessed;

	private String reasonToReject;

	private Status status;

	private String uid;

	private List<File4DocDTO> filesAttached;

	@JsonIgnore
	private List<File4DB> fileList;

	public DocumentDetailsDTO(Document document) {
		super();
		this.name = document.getName();
		this.author = document.getAuthor().getName() + " " + document.getAuthor().getSurname();
		this.dType = document.getdType();
		this.dateCreate = document.getDateCreate();
		this.description = document.getDescription();
		this.dateSubmit = document.getDateSubmit();
		this.dateProcessed = document.getDateProcessed();
		this.reasonToReject = document.getReasonToReject();
		if (document.getHandler() != null) {
			this.processedBy = document.getHandler().getName() + " " + document.getHandler().getSurname();
		}
		else {
			this.processedBy = null;
		}
		this.status = document.getStatus();
		this.fileList = document.getFileList();
		this.uid = document.getUID();
		this.type = document.getdType().getName();
		this.filesAttached = getFileNames(document.getFileList());
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<File4DB> getFileList() {
		return fileList;
	}

	public void setFileList(List<File4DB> fileList) {
		this.fileList = fileList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public DocType getdType() {
		return dType;
	}

	public void setdType(DocType dType) {
		this.dType = dType;

//	public boolean checkDocument(@PathVariable("name") String name) {
//		if (docService.findDocumentByName(name) != null) {
//			return true;
//		}
//		return false;
//	}
	}

	public List<File4DocDTO> getFilesAttached() {
		return filesAttached;
	}

	public void setFilesAttached(List<File4DocDTO> filesAttached) {
		this.filesAttached = filesAttached;
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

	public String getProcessedBy() {
		return processedBy;
	}

	public void ProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private List<File4DocDTO> getFileNames(List<File4DB> files) {
		List<File4DocDTO> fileNamesAttached = new ArrayList<>();
		for (File4DB file : files) {
			fileNamesAttached.add(new File4DocDTO(file.getFileName().substring(18), file.getUID(), file.getFileSize()));
		}
		return fileNamesAttached;
	}

}
