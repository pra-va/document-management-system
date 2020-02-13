package lt.vtmc.docTypes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.docTypes.model.DocType;

public interface DocTypeRepository extends JpaRepository<DocType, String>{

	DocType findDocTypeByName(String name);

}
