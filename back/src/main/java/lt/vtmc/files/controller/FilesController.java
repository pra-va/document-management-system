package lt.vtmc.files.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.documents.model.Document;
import lt.vtmc.files.service.FileService;
import lt.vtmc.user.controller.UserController;

/**
 * Rest controller for downloading and uploading files.
 * 
 * @author pra-va
 *
 */
@RestController
@CrossOrigin(value = { "*" }, exposedHeaders = { "Content-Disposition" })
public class FilesController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private FileService fileService;

	/**
	 * API method that accepts single multipart file and calls service layer method
	 * for this file to be preserved on database.
	 * 
	 * @param file
	 * @return
	 */
	public void uploadFiles(MultipartFile file, Document doc) {
//		for (MultipartFile file : files) {
		try {
			LOG.info("File uploaded with file name: " + file.getOriginalFilename());
			fileService.saveFile(file, doc);
		} catch (Exception e) {
			LOG.error("Error saving file", e);
		}
//		}
	}

	/**
	 * This method is responsible for accepting an array of multipart files and
	 * saving them to database by calling uploadFile method for each single
	 * multipart file.
	 * 
	 * @param files
	 * @return
	 */
//	@PostMapping("/api/files")
//	public List<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		System.out.println(Arrays.toString(files));
//		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
//	}

	/**
	 * This API method will return a downloadable file by file name.
	 * 
	 * @param fileName
	 * @return
	 */
	@GetMapping("/api/file/{fileName}")
	public ResponseEntity<Resource> downloadFileByFileName(String fileName) {
		return fileService.downloadFileByName(fileName);
	}

}
