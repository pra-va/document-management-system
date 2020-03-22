package lt.vtmc.docTypes.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.docTypes.model.DocType;

public interface DocTypeRepository extends JpaRepository<DocType, String> {

	DocType findDocTypeByName(String name);

	@Query("SELECT d FROM DocType d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	Page<DocType> findLike(String searchValueString, Pageable firstPageable);

}
