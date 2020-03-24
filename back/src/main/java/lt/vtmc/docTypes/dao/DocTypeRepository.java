package lt.vtmc.docTypes.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.docTypes.model.DocType;

public interface DocTypeRepository extends JpaRepository<DocType, String> {

	DocType findDocTypeByName(String name);

	@Query("SELECT d FROM DocType d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<DocType> findLike(String searchValueString, Pageable firstPageable);

	@Query("select dt.name from DocType dt where LOWER(dt.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<String> findLikeDocTypeNames(String searchValuString, Pageable pageable);

	@Query("select ga.name from DocType d join d.groupsApproving ga where d.name = ?1")
	List<String> findGroupsApprovingByDocTypeName(String name);

	@Query("select gc.name from DocType d join d.groupsCreating gc where d.name = ?1")
	List<String> findGroupsCreatingByDocTypeName(String name);

}
