package lt.vtmc.files.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.files.dao.FilesRepository;
import lt.vtmc.files.model.File4DB;
import lt.vtmc.user.controller.UserController;

@Service
public class FileService {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private FilesRepository filesRepository;

	@Transactional
	public void saveMultipleFiles(MultipartFile[] files) throws Exception {
		for (int i = 0; i < files.length; i++) {
			saveFile(files[i]);
		}
	}

	@Transactional
	public void saveFile(MultipartFile file) throws Exception {
		LOG.info("saving file to db");
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		String fileType = file.getContentType();
		File4DB file4db = new File4DB(fileName, fileType, bytes);
		filesRepository.save(file4db);
	}

	@Transactional
	public ResponseEntity<Resource> downloadFileByName(String fileName) {
		File4DB file4db = filesRepository.findFile4dbByFileName(fileName);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file4db.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file4db.getFileName() + "\"")
				.body(new ByteArrayResource(file4db.getData()));
	}

}
