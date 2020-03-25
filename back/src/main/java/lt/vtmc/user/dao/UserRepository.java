package lt.vtmc.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.documents.Status;
import lt.vtmc.documents.model.Document;
import lt.vtmc.statistics.dto.StatisticsDocTypeDTO;
import lt.vtmc.statistics.dto.StatisticsUserDTO;
import lt.vtmc.user.dto.UserNoGroupsDTO;
import lt.vtmc.user.model.User;

/**
 * User repositroy.
 * 
 * @author pra-va
 *
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findUserByUsername(String username);

	/**
	 * Finds if user with such username as provided in parameters exists.
	 * 
	 * @param username
	 * @return
	 */
	@Query("select case when count(u.username) > 0 then true else false end from  User u where u.username = ?1")
	boolean isUsernameExists(String username);

	/**
	 * Finds user list with pageable parameter and provided search phrase which can
	 * be name, surname, username or role.
	 * 
	 * @param searchValueString
	 * @param firstPageable
	 * @return users page
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.name) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.surname) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.role) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<User> findLike(String searchValueString, Pageable firstPageable);

	/**
	 * Finds users and returns them without groups by creating new DTO object. Also
	 * accepts search phrase and pageable parameters.
	 * 
	 * @param searchPhrase
	 * @param page
	 * @return
	 */
	@Query("select new lt.vtmc.user.dto.UserNoGroupsDTO(u.username, u.name, u.surname, u.role) from User u where LOWER(u.username) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.name) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.surname) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.role) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<UserNoGroupsDTO> findLikeNoGroups(String searchPhrase, Pageable page);

	/**
	 * Finds a list of document types' names that user can create as a page of
	 * strings.
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param pageable
	 * @return page of names of document types
	 */
	@Query("select distinct d.name from DocType d inner join d.groupsCreating g inner join g.userList u where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%'))")
	Page<String> docTypesUserCreatesByUsername(String username, String searchPhrase, Pageable pageable);

	/**
	 * Finds a list of document types' names that user can sign as a page of
	 * strings.
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param pageable
	 * @return page of names of document types
	 */
	@Query("select distinct d, u.username, a.name, a.surname, dt.name from Document d inner join d.author a inner join d.dType dt inner join dt.groupsApproving g inner join g.userList u where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateSubmit) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(CONCAT(a.name, ' ', a.surname)) LIKE LOWER(CONCAT('%', ?2,'%'))) and d.status = 1")
	Page<Document> docsToSignByUsername(String username, String searchPhrase, Pageable pageable);

	/**
	 * Finds documents by username. Uses pageble interface and also accepts custom
	 * search phrase.
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param pageable
	 * @return page of documents that user is able to create
	 */
	@Query("select d, dt from User u inner join u.createdDocuments d inner join d.dType dt where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateCreate) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%')))")
	Page<Document> docsByUsername(String username, String searchPhrase, Pageable pageable);

	/**
	 * 
	 * Will return documents by username and document status (CREATED, SUBMITTED,
	 * ACCEPTED, REJECTED).
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param status
	 * @param pageable
	 * @return page of documents
	 */
	@Query("select d from User u inner join u.createdDocuments d inner join d.dType dt where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateCreate) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%'))) and d.status = ?3")
	Page<Document> docsByUsernameAndStatus(String username, String searchPhrase, Status status, Pageable pageable);

	/**
	 * 
	 * Finds users document types that she/he was able to validate and calculates
	 * how many times each user that created this type of document, submitted v
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param pageable
	 * @return
	 */
	@Query("select new lt.vtmc.statistics.dto.StatisticsUserDTO(a.username as username, a.name as name, a.surname as surname, count(distinct d.id) as docNumber) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d join d.author a where u.username = ?1 and (LOWER(a.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(a.surname) LIKE LOWER(CONCAT('%', ?2,'%'))) and (d.status = '1' or d.status = '2' or d.status = '3') group by a.username")
	Page<StatisticsUserDTO> statisticsByUsers(String username, String searchPhrase, Pageable pageable);

	/**
	 * 
	 * Finds statistics data for user of his/her doc types that he can validate. For
	 * every document type of user, provided in parameters, returns count of
	 * documents with SUBMITTED, ACCEPTED OR REJECTED status that user can validate.
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	@Query("select new lt.vtmc.statistics.dto.StatisticsDocTypeDTO(da.name as docTypeName, sum(case when d.status = '1' then 1 else 0 end) as submited, sum(case when d.status = '2' then 1 else 0 end) as accepted, sum(case when d.status = '3' then 1 else 0 end) as declined) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) and substring(d.dateSubmit, 1, 10) between substring(?3, 1, 10) and substring(?4, 1, 10) group by da.name")
	Page<StatisticsDocTypeDTO> statisticsByDocType(String username, String searchPhrase, String startDate,
			String endDate, Pageable pageable);

	/**
	 * 
	 * Finds statistics data for user of his/her document type creating users stats
	 * (number of created documents).
	 * 
	 * @param username
	 * @param searchPhrase
	 * @param pageable
	 * @return
	 */
	@Query("select new lt.vtmc.statistics.dto.StatisticsDocTypeDTO(da.name as docTypeName, sum(case when d.status = '1' then 1 else 0 end) as submited, sum(case when d.status = '2' then 1 else 0 end) as accepted, sum(case when d.status = '3' then 1 else 0 end) as declined) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) group by da.name")
	Page<StatisticsDocTypeDTO> statisticsByDocType(String username, String searchPhrase, Pageable pageable);

	/**
	 * 
	 * @return count of users with admin role.
	 */
	@Query("select count(u) from User u where u.role = 'ADMIN'")
	int countUsers();

}