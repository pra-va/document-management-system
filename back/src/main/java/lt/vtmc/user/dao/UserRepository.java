package lt.vtmc.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.user.model.User;

/**
 * User repositroy.
 * 
 * @author pra-va
 *
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findUserByUsername(String username);

	
	@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', ?1,'%'))")
	
	Page<User> findLike(String searchValueString, Pageable firstPageable);
}