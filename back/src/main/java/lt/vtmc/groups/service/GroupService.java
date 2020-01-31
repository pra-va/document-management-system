package lt.vtmc.groups.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;

/**
 * Group service for creating and managing groups.
 * 
 * @author VStoncius
 *
 */
@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * This method finds groups from group repository.
	 */
	public Group findGroupByName(String name) {
		return groupRepository.findGroupByName(name);
	}

	/**
	 * Method to create user groups.
	 * 
	 * @return Group
	 */
	@Transactional
	public Group createGroup(String name, String description) {
		Group newGroup = new Group();
		newGroup.setName(name);
		newGroup.setDescription(description);
		groupRepository.save(newGroup);
		return newGroup;

	}

	/**
	 * Method to add users to groups.
	 * 
	 * @param names
	 * @param username
	 */
	@Transactional
	public void addUserToGroup(String[] names, String username) {
		User userToAdd = userRepository.findUserByUsername(username);
		for (int i = 0; i < names.length; i++) {
			Group groupToAddTo = groupRepository.findGroupByName(names[i]);
			List<User> tmpUserList = groupToAddTo.getUserList();
			List<Group> tmpGroupList = userToAdd.getGroupList();
			if (tmpUserList.contains(userToAdd) == false && tmpGroupList.contains(groupToAddTo) == false) {
				tmpGroupList.add(groupToAddTo);
				userToAdd.setGroupList(tmpGroupList);
				tmpUserList.add(userToAdd);
				groupToAddTo.setUserList(tmpUserList);
				}
		}
	}

	public List<Group> retrieveAllGroups() {
		List<Group> grouplist = groupRepository.findAll();
		return grouplist;
	}
}
