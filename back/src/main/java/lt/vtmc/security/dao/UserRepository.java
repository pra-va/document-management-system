package lt.vtmc.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.security.model.User;

/**
 * User repositroy.
 * 
 * @author pra-va
 *
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findUserByUsername(String username);

}
