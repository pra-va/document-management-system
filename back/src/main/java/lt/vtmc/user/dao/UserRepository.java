package lt.vtmc.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.documents.model.Document;
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

	@Query("select d.name from DocType d inner join d.groupsCreating g inner join g.userList u where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%'))")
	Page<String> docTypesUserCreatesByUsername(String username, String searchPhrase, Pageable pageable);

	@Query("select d from Document d inner join d.dType dt inner join dt.groupsApproving g inner join g.userList u "
			+ "where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) " + "and d.status = 1")
	Page<Document> docsToSignByUsername(String username, String searchPhrase, Pageable pageable);

	@Query("select d from User u inner join u.createdDocuments d inner join d.dType dt where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%'))")
	Page<Document> docsByUsername(String username, String searchPhrase, Pageable pageable);

//	@Query<>
//	Page<String> allDocuments(String username, Pageable pageable);
}