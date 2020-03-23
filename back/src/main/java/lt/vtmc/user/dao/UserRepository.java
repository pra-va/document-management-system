package lt.vtmc.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.documents.Status;
import lt.vtmc.documents.model.Document;
import lt.vtmc.statistics.dto.StatisticsDocTypeDTO;
import lt.vtmc.statistics.dto.StatisticsUserDTO;
import lt.vtmc.user.model.User;

/**
 * User repositroy.
 * 
 * @author pra-va
 *
 */
public interface UserRepository extends JpaRepository<User, String> {
	User findUserByUsername(String username);

	@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.name) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.surname) LIKE LOWER(CONCAT('%', ?1,'%')) OR LOWER(u.role) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<User> findLike(String searchValueString, Pageable firstPageable);

	@Query("select distinct d.name from DocType d inner join d.groupsCreating g inner join g.userList u where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%'))")
	Page<String> docTypesUserCreatesByUsername(String username, String searchPhrase, Pageable pageable);

	@Query("select distinct d, u.username, a.name, a.surname, dt.name from Document d inner join d.author a inner join d.dType dt inner join dt.groupsApproving g inner join g.userList u where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateSubmit) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(CONCAT(a.name, ' ', a.surname)) LIKE LOWER(CONCAT('%', ?2,'%'))) and d.status = 1")
	Page<Document> docsToSignByUsername(String username, String searchPhrase, Pageable pageable);

	@Query("select d, dt from User u inner join u.createdDocuments d inner join d.dType dt where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateCreate) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%')))")
	Page<Document> docsByUsername(String username, String searchPhrase, Pageable pageable);

	@Query("select d from User u inner join u.createdDocuments d inner join d.dType dt where u.username = ?1 and (LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.dateCreate) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(dt.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(d.UID) LIKE LOWER(CONCAT('%', ?2,'%'))) and d.status = ?3")
	Page<Document> docsByUsernameAndStatus(String username, String searchPhrase, Status status, Pageable pageable);

	@Query("select new lt.vtmc.statistics.dto.StatisticsUserDTO(a.username as username, a.name as name, a.surname as surname, count(distinct d.id) as docNumber) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d join d.author a where u.username = ?1 and (LOWER(a.name) LIKE LOWER(CONCAT('%', ?2,'%')) or LOWER(a.surname) LIKE LOWER(CONCAT('%', ?2,'%'))) and (d.status = '1' or d.status = '2' or d.status = '3') group by a.username")
	Page<StatisticsUserDTO> statisticsByUsers(String username, String searchPhrase, Pageable pageable);

	@Query("select new lt.vtmc.statistics.dto.StatisticsDocTypeDTO(da.name as docTypeName, sum(case when d.status = '1' then 1 else 0 end) as submited, sum(case when d.status = '2' then 1 else 0 end) as accepted, sum(case when d.status = '3' then 1 else 0 end) as declined) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) and substring(d.dateSubmit, 1, 10) between substring(?3, 1, 10) and substring(?4, 1, 10) group by da.name")
	Page<StatisticsDocTypeDTO> statisticsByDocType(String username, String searchPhrase, String startDate,
			String endDate, Pageable pageable);

	@Query("select new lt.vtmc.statistics.dto.StatisticsDocTypeDTO(da.name as docTypeName, sum(case when d.status = '1' then 1 else 0 end) as submited, sum(case when d.status = '2' then 1 else 0 end) as accepted, sum(case when d.status = '3' then 1 else 0 end) as declined) from User u join u.groupList g join g.docTypesToApprove da join da.documentList d where u.username = ?1 and LOWER(d.name) LIKE LOWER(CONCAT('%', ?2,'%')) group by da.name")
	Page<StatisticsDocTypeDTO> statisticsByDocType(String username, String searchPhrase, Pageable pageable);

	@Query("select count(u) from User u where u.role = 'ADMIN'")
	int countUsers();

}