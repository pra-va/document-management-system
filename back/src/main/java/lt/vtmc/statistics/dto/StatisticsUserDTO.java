package lt.vtmc.statistics.dto;

public class StatisticsUserDTO {
	private String username;
	private String name;
	private String surname;
	private long docNumber;

	public StatisticsUserDTO() {
	}

	public StatisticsUserDTO(String username, String name, String surname, long docNumber) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.docNumber = docNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(long docNumber) {
		this.docNumber = docNumber;
	}

}
