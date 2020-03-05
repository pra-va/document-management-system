package lt.vtmc.documents.dto;

public class File4DocDTO {

	private String fileName;
	private long fileSize;
	private String UID;

	public File4DocDTO(String fileName, String uID, long fileSize) {
		this.fileName = fileName;
		this.UID = uID;
		this.fileSize = fileSize;
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

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

}
