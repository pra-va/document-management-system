package lt.vtmc.documents.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.documents.model.Document;

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

	@Query("select case when (count(d.UID) = 1) then true else false end from User u join u.createdDocuments d where u.username = ?2 and d.UID = ?1 and d.status = '0'")
	boolean doesUserHaveDoc(String uid, String username);

	@Transactional
	@Modifying
	@Query("delete from Document d where d.UID = ?1")
	void deleteUsersDocument(String uid);
}
