package lt.vtmc.restApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.restApi.model.User;

/**
 * User repositroy.
 * 
 * @author pra-va
 *
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findUserByUsername(String username);

}
