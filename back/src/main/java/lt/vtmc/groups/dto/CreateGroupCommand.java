package lt.vtmc.groups.dto;

public class CreateGroupCommand {

	private String name;
	
	private String description;
	/**
	 * Constructor method for CreateGroupCommand
	 * 
	 * @param name
	 * @param description
	 */
	public CreateGroupCommand(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	/**
	 * 
	 * @param name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @return description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
