package lt.vtmc.documents.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.documents.model.Document;

/**
 * Group repository.
 * 
 * @author VStoncius
 *
 */

public interface DocumentRepository extends JpaRepository<Document, String> {
	Document findDocumentByName(String name);
}
