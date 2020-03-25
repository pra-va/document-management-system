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
	Group findGroupByName(String name);

	Page<Group> findAllByName(String name, Pageable pageable);

	@Query("SELECT g FROM Group g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<Group> findLike(String name, Pageable pageable);
}
