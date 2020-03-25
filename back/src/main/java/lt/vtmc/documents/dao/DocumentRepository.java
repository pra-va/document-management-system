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
	
	/**
	 * Finds document by name from the database
	 * 
	 * @param name of the document
	 * @return document entity type object
	 */
	Document findDocumentByName(String name);

	/**
	 * Finds document by UID from the database
	 * 
	 * @param UID unique id of the document
	 * @return document entity type object
	 */
	Document findDocumentByUID(String UID);

	/**
	 * Finds all documents by document type from the database
	 *
	 * @param dType document type to find by 
	 * @return list of document entity type objects
	 */
	List<Document> findAllBydType(DocType dType);
	
	/**
	 * Finds all documents containing search phrase within their name
	 *
	 * @param searchValueString to search database for
	 * @param firstPageable Sets response list size, sort order and search phrase
	 * @return page of document entity type objects
	 */
	@Query("SELECT d FROM Document d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', ?1,'%'))")

	Page<Document> findLike(String searchValueString, Pageable firstPageable);

	/**
	 * Checks if the user is the author of the document
	 *
	 * @param uid unique id of the document
	 * @param username unique users' name in the system
	 * @return true or false
	 */
	@Query("select case when (count(d.UID) = 1) then true else false end from User u join u.createdDocuments d where u.username = ?2 and d.UID = ?1 and d.status = '0'")
	boolean doesUserHaveDoc(String uid, String username);


	/**
	 * Deletes document from database by UID
	 *
	 * @param uid unique id of the document
	 */
	@Transactional
	@Modifying
	@Query("delete from Document d where d.UID = ?1")
	void deleteUsersDocument(String uid);
}
