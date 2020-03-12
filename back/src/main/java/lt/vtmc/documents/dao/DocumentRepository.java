package lt.vtmc.documents.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.model.Document;
import lt.vtmc.user.model.User;

/**
 * Group repository.
 * 
 * @author VStoncius
 *
 */

public interface DocumentRepository extends JpaRepository<Document, String> {
	Document findDocumentByName(String name);

	Document findDocumentByUID(String UID);
	
	List<Document> findAllBydType(DocType dType);
	

	@Query("SELECT d FROM Document d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
	
	Page<Document> findLike(String searchValueString, Pageable firstPageable);
}
