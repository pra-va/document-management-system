package lt.vtmc.groups.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.groups.model.Group;

/**
 * Group repository.
 * 
 * @author VStoncius
 *
 */
public interface GroupRepository extends JpaRepository<Group, String> {
	Group findGroupByName(String name);

}
