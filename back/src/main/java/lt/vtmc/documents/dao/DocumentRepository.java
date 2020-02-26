package lt.vtmc.documents.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
