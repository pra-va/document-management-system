package lt.vtmc.groups.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.groups.model.Group;

/**
 * Group repository.
 * 
 * @author VStoncius
 *
 */
public interface GroupRepository extends JpaRepository<Group, String> {
	/**
	 * Returns a group by name
	 * 
	 * @param name of the group
	 * @return Group entity type object
	 */
	Group findGroupByName(String name);

	/**
	 * Returns all groups
	 * 
	 * @param name search phrase
	 * @param pageable Sets response list size, sort order and search phrase
	 * @return page of group entity type objects
	 */
	Page<Group> findAllByName(String name, Pageable pageable);

	/**
	 * Returns groups filtered by name
	 * 
	 * @param name of the group
	 * @param pageable Sets response list size, sort order and search phrase
	 * @return page of group entity type objects
	 */
	@Query("SELECT g FROM Group g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<Group> findLike(String name, Pageable pageable);
}
