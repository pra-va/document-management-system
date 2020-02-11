package lt.vtmc.documents.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.documents.model.DocType;

public interface DocTypeRepository extends JpaRepository<DocType, String>{

	DocType findDocTypeByName(String name);

}
