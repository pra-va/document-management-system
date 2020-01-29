package lt.vtmc.groups.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.groups.dto.CreateGroupCommand;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;

/**
 * Controller for managing groups.
 * 
 *@author VStoncius
 *
 */
@RestController
public class GroupController {

	@Autowired
	private GroupService groupService;

	/**
	 * Creates group with ADMIN role. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/creategroup
	 * @method POST
	 * @param user details
	 */
	@RequestMapping(path = "/api/creategroup", method = RequestMethod.POST)
	public ResponseEntity<String> createGroup(@RequestBody CreateGroupCommand command) {
		if (groupService.findGroupByName(command.getName()) == null) {
			groupService.createGroup(command.getName(), command.getDescription());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(path = "/api/groups", method = RequestMethod.GET)
	public List<Group> listAllGroups(){
		return groupService.retrieveAllGroups();
	}
}
