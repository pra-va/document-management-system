package lt.vtmc.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.Status;
import lt.vtmc.documents.dao.DocumentRepository;
import lt.vtmc.documents.dto.DocumentDetailsDTO;
import lt.vtmc.documents.model.Document;
import lt.vtmc.groups.model.Group;
import lt.vtmc.paging.PagingData;
import lt.vtmc.paging.PagingResponse;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.dto.UserDetailsDTO;
import lt.vtmc.user.dto.UserNoGroupsDTO;
import lt.vtmc.user.model.User;

/**
 * User service class to create and manipulate user instaces.
 * 
 * @author pra-va
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DocumentRepository docRepo;

	/**
	 * Will return User object based user found by
	 * lt.vtmc.security.service.UserService.findUserByUsername(String).
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User newUser = userRepository.findUserByUsername(username);
		if (newUser == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			StringBuilder strBuild = new StringBuilder();
			char[] passArray = newUser.getPassword().toCharArray();
			for (int i = 0; i < passArray.length - 60; i++) {
				strBuild.append(passArray[i]);
			}
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), strBuild.toString(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole() }));
		}
	}

	/**
	 * This method finds users from user repository.
	 * 
	 * @param username to be looked up
	 * @return User object
	 */
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 * Will check if such user name exists. This method should be used when creating
	 * and updating user to avoid duplicate user names.
	 * 
	 * @param username to be checked if it exists
	 * @return true if username exists
	 */
	public boolean checkIfUsernameExists(String username) {
		return userRepository.isUsernameExists(username);
	}

	/**
	 * Method to create users.
	 * 
	 * @param username of the new user
	 * @param name     of the new user
	 * @param surname  of the new user
	 * @param password of the new user
	 * @return new user object that was created
	 */
	@Transactional
	public User createUser(String username, String name, String surname, String password) {
		User newUser = new User(username, name, surname, password, "USER");
		List<Group> tmpList = new ArrayList<Group>();
		newUser.setGroupList(tmpList);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(password) + "$2y$10$h3WjpIAbYUZYDLFa00sky.yVccPlkZGsFtAEl3zlISco7KlyYroGm");
		userRepository.save(newUser);
		return newUser;
	}

	/**
	 * Method to create system administrators.
	 * 
	 * @param username of the new administrator
	 * @param name     of the new administrator
	 * @param surname  of the new administrator
	 * @param password of the new administrator
	 * @return new administrator object that was created
	 */
	@Transactional
	public User createSystemAdministrator(String username, String name, String surname, String password) {
		User newUser = new User(username, name, surname, password, "ADMIN");
		List<Group> tmpList = new ArrayList<Group>();
		newUser.setGroupList(tmpList);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(
				encoder.encode(password) + "$2y$10$h3WjpIAbYUZYDLFa00sky.yVccPlkZGsFtAEl3zlISco7KlyYroGm" + "");
		newUser.setProcessedDocuments(new ArrayList<Document>());
		newUser.setCreatedDocuments(new ArrayList<Document>());
		userRepository.save(newUser);
		System.out.println(newUser.toString());
		return newUser;
	}

	/**
	 * Returns all users as page requested in parameters.
	 * 
	 * @param pagingData to set response list size, sort order and search phrase
	 * @return map of users and paging data
	 */
	public Map<String, Object> retrieveAllUsers(PagingData pagingData) {
		Pageable firstPageable = pagingData.getPageable();
		Page<User> userlist = userRepository.findLike(pagingData.getSearchValueString(), firstPageable);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("pagingData",
				new PagingResponse(userlist.getNumber(), userlist.getTotalElements(), userlist.getSize()));
		responseMap.put("userList",
				userlist.getContent().stream().map(user -> new UserDetailsDTO(user)).collect(Collectors.toList()));
		return responseMap;
	}

	/**
	 * Method to delete system users.
	 * 
	 * @param user to be deleted
	 */
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	/**
	 * Method to update system user details.
	 * 
	 * @param username to be updated
	 * @param name     of updated user
	 * @param surname  of updated user
	 * @param password of updated user
	 * @param role     of updated user
	 * @return updated user
	 */
	@Transactional
	public User updateUserDetails(String username, String name, String surname, String password, String role) {
		User updatedUser = userRepository.findUserByUsername(username);
		updatedUser.setName(name);
		updatedUser.setSurname(surname);
		if (!password.equals("") && password.length() > 7 && password.length() < 21) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			updatedUser.setPassword(
					encoder.encode(password) + "$2y$10$h3WjpIAbYUZYDLFa00sky.yVccPlkZGsFtAEl3zlISco7KlyYroGm");
		}
		updatedUser.setRole(role);
		userRepository.save(updatedUser);
		return updatedUser;
	}

	/**
	 * Returns document types that user provided in parameters can create.
	 * 
	 * @param username   to get doc types that he or she can create
	 * @param pagingData to set response list size, sort order and search phrase
	 * @return document types that user can create and paging information
	 */
	public Map<String, Object> getUserDocTypesToCreate(String username, PagingData pagingData) {
		Pageable pageable = pagingData.getPageable();
		Page<String> docTypeNames = userRepository.docTypesUserCreatesByUsername(username,
				pagingData.getSearchValueString(), pageable);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("pagingData",
				new PagingResponse(docTypeNames.getNumber(), docTypeNames.getTotalElements(), docTypeNames.getSize()));
		responseMap.put("docTypes", docTypeNames.getContent());
		return responseMap;
	}

	/**
	 * Returns document types that user provided in parameters can sign.
	 * 
	 * @param username to get doc types that he or she can sign
	 * @return document types that user can sign and paging information
	 */
	public List<DocumentDetailsDTO> getUserDocumentsToBeSigned(String username) {
		User tmpUser = userRepository.findUserByUsername(username);
		List<Group> tmpGroupList = tmpUser.getGroupList();

		List<DocType> tmpList = new ArrayList<DocType>();
		for (Group group : tmpGroupList) {
			tmpList.addAll(group.getDocTypesToApprove());
		}

		List<Document> tmpListDoc = new ArrayList<Document>();
		for (DocType dType : tmpList) {
			tmpListDoc.addAll(docRepo.findAllBydType(dType));
		}

		List<DocumentDetailsDTO> listToReturn = new ArrayList<DocumentDetailsDTO>();
		for (Document document : tmpListDoc) {
			if (document.getStatus() == Status.SUBMITTED) {
				listToReturn.add(new DocumentDetailsDTO(document));
			}
		}
		return listToReturn;
	}

	/**
	 * This method will return true if there is initial user created and otherwise -
	 * false.
	 * 
	 * @return true if there is no initial system administrator
	 */
	public boolean shouldCreateFirstUser() {
		int usersCount = userRepository.countUsers();
		if (usersCount == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method will find users and return them without groups by provided
	 * username and paging data.
	 * 
	 * @param searchPhrase to find
	 * @param pagingData   to set response list size, sort order and search phrase
	 * @return users, their information without groups and pagign data
	 */
	public Map<String, Object> getUsersNoGroups(String searchPhrase, PagingData pagingData) {
		Pageable firstPageable = pagingData.getPageable();
		Page<UserNoGroupsDTO> userlist = userRepository.findLikeNoGroups(pagingData.getSearchValueString(),
				firstPageable);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("pagingData",
				new PagingResponse(userlist.getNumber(), userlist.getTotalElements(), userlist.getSize()));
		responseMap.put("userList", userlist);
		return responseMap;
	}

}
