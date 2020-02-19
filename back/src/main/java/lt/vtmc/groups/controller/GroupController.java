package lt.vtmc.groups.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.groups.dto.CreateGroupCommand;
import lt.vtmc.groups.dto.GroupDetailsDTO;
import lt.vtmc.groups.dto.UpdateGroupCommand;
import lt.vtmc.groups.model.Group;
import lt.vtmc.groups.service.GroupService;
import lt.vtmc.user.controller.UserController;
import lt.vtmc.user.model.User;
import lt.vtmc.user.service.UserService;

/**
 * Controller for managing groups.
 * 
 * @author VStoncius
 *
 */
@RestController
public class GroupController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

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
		if (groupService.findGroupByName(command.getGroupName()) == null) {
			groupService.createGroup(command.getGroupName(), command.getDescription());
			groupService.addUsersToGroup(command.getGroupName(), command.getUserList());
			groupService.addDocTypes(command.getGroupName(), command.getDocTypesToSign(), command.getDocTypesToCreate());
			LOG.info("# LOG # Initiated by [{}]: Group [{}] was created #",
					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());

			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else

			LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT created #",
					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());
		return new ResponseEntity<String>("Failed to create group", HttpStatus.CONFLICT);
	}

	@RequestMapping(path = "/api/groups", method = RequestMethod.GET)
	public List<GroupDetailsDTO> listAllGroups() {

		LOG.info("# LOG # Initiated by [{}]: requested list of all groups #",
				SecurityContextHolder.getContext().getAuthentication().getName());

		return groupService.retrieveAllGroups();
	}

	@GetMapping(path = "/api/groups/{groupname}")
	public GroupDetailsDTO findGroupByName(@PathVariable("groupname") String name) {

		LOG.info("# LOG # Initiated by [{}]: Searched for group [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);

		return new GroupDetailsDTO(groupService.findGroupByName(name));
	}

	@PostMapping(path = "/api/addGroup/{username}")
	public ResponseEntity<String> addGroup(@PathVariable("username") String username, @RequestBody String[] names) {
		if (userService.findUserByUsername(username) != null) {
			groupService.addUserToGroupByUsername(names, username);

			LOG.info("# LOG # Initiated by [{}]: User [{}] was added to group(s) [{}] #",
					SecurityContextHolder.getContext().getAuthentication().getName(), username, Arrays.toString(names));

			return new ResponseEntity<String>("User added succesfully", HttpStatus.OK);
		}

		LOG.info("# LOG # Initiated by [{}]: User [{}] was NOT added to group(s) [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), username, Arrays.toString(names));

		return new ResponseEntity<String>("Failed to add user to group", HttpStatus.NOT_FOUND);
	}

	/**
	 * Updates group information
	 * 
	 * @url /api/groups/{groupname}
	 * @method POST
	 */
	@PostMapping(path = "/api/groups/update/{groupname}")
	public ResponseEntity<String> updateGroupByGroupName(@RequestBody UpdateGroupCommand command,
			@PathVariable("groupname") String name) {
		if (groupService.findGroupByName(name) != null) {
			groupService.updateGroupDetails(command.getNewName(), name, command.getDescription(), command.getUserList(),
					command.getDocTypesToApprove(), command.getDocTypesToCreate());
			groupService.addDocTypes(command.getNewName(), command.getDocTypesToApprove(), command.getDocTypesToCreate());
			LOG.info("# LOG # Initiated by [{}]: Group [{}] was updated #",
					SecurityContextHolder.getContext().getAuthentication().getName(), name);

			return new ResponseEntity<String>("Updated succesfully", HttpStatus.ACCEPTED);
		}

		LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT updated - [{}] was NOT found #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name, name);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}

	/**
	 * Checks if provided groupname exists.
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping(path = "/api/group/{name}/exists")
	public boolean groupNameExists(@PathVariable("name") String name) {
		if (this.groupService.findGroupByName(name) != null) {
			return true;
		} else {
			return false;
		}
	}

	@DeleteMapping("/api/group/{groupname}/delete")
	public ResponseEntity<String> deleteGroupByName(@PathVariable("groupname") String groupname) {
		Group tmpGroup = groupService.findGroupByName(groupname);
		if (tmpGroup != null) {

			LOG.info("# LOG # Initiated by [{}]: User [{}] was deleted #",
					SecurityContextHolder.getContext().getAuthentication().getName(), tmpGroup);

			groupService.deleteGroup(tmpGroup);
			return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
		}

		LOG.info("# LOG # Initiated by [{}]: User [{}] was NOT deleted - [{}] was NOT found #",
				SecurityContextHolder.getContext().getAuthentication().getName(), groupname, groupname);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}
}
