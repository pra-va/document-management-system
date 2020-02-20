package lt.vtmc.files.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.files.model.File4DB;

public interface FilesRepository extends JpaRepository<File4DB, Integer> {
	File4DB findFile4dbByFileName(String fileName);

}
