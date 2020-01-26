package lt.vtmc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.restApi.dao.UserRepository;
import lt.vtmc.restApi.model.User;

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

	/**
	 * Will return User object based user found by
	 * {@link lt.vtmc.security.service.UserService.findUserByUsername(String)}.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User newUser = findUserByUsername(username);
		if (newUser == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole() }));
		}
	}

	/**
	 * This method finds users from user repository.
	 */
	@Transactional
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
}
