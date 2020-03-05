package lt.vtmc.files.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lt.vtmc.documents.model.Document;
import lt.vtmc.files.model.File4DB;

public class FileDetailsDTO {

	private String fileName; //

	private String fileType; //

	private long fileSize;

	@JsonIgnore
	private Document document; //

	private String UID; //

	public FileDetailsDTO(File4DB file) {
		this.fileName = file.getFileName();
		this.fileType = file.getFileType();
		this.document = file.getDocument();
		UID = file.getUID();
		file.getFileSize();
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getCsvDetails() {
		return UID + ", " + fileName.replace(UID + "-", "") + ", " + document.getUID() + ", " + document.getName()
				+ ", " + document.getDateCreate() + "\n";
	}

}
