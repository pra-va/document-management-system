package lt.vtmc.groups.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.documents.model.DocType;
import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.dto.GroupDetails;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;
import lt.vtmc.user.service.UserService;

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
		newGroup.setDocTypesToApprove(new ArrayList<DocType>());
		newGroup.setDocTypesToCreate(new ArrayList<DocType>());
		newGroup.setUserList(new ArrayList<User>());
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
	public void addUserToGroupByUsername(String[] groupList, String username) {
		User userToAdd = userRepository.findUserByUsername(username);
		for (int i = 0; i < groupList.length; i++) {
			Group groupToAddTo = groupRepository.findGroupByName(groupList[i]);
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

	/**
	 * Method to add users to groups.
	 * 
	 * @param names
	 * @param username
	 */
	@Transactional
	public void addUsersToGroup(String groupname, String[] userlist) {
		Group groupToAddTo = groupRepository.findGroupByName(groupname);
		for (int i = 0; i < userlist.length; i++) {
			User userToAdd = userRepository.findUserByUsername(userlist[i]);
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

	public String[] retrieveAllGroups() {
		List<Group> grouplist = groupRepository.findAll();
		String[] details = new String[grouplist.size()];
		for (int i = 0; i < grouplist.size(); i++) {
			details[i] = new GroupDetails(grouplist.get(i)).toString();
		}
		return details;
	}
	
}
