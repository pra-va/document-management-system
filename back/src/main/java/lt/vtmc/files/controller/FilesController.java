package lt.vtmc.files.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.documents.model.Document;
import lt.vtmc.files.dto.FileDetailsDTO;
import lt.vtmc.files.service.FileService;
import lt.vtmc.files.service.ZipService;
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

	@Autowired
	private ZipService zipService;

	/**
	 * API method that accepts single multipart file and calls service layer method
	 * for this file to be preserved on database.
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/api/file")
	public void uploadFiles(MultipartFile file, Document doc) {
		try {
			LOG.info("# LOG # Initiated by [{}]: User uploaded file with filename: [{}]#",
					SecurityContextHolder.getContext().getAuthentication().getName(), file.getOriginalFilename());
			fileService.saveFile(file, doc);
		} catch (Exception e) {
			LOG.error("Error saving file", e);
		}
	}

	/**
	 * This API method will return a downloadable file by file name.
	 * 
	 * @param fileName
	 * @return
	 */
	@GetMapping("/api/files/{fileUID}")
	public ResponseEntity<Resource> downloadFileByFileName(@PathVariable("fileUID") String fileUID) {
		LOG.info("# LOG # Initiated by [{}]: User downloaded file with file UID: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), fileUID);
		return fileService.downloadFileByUID(fileUID);
	}

	@GetMapping("/api/files/info/username/{username}")
	public List<FileDetailsDTO> findAllFIleDetailsByUsername(@PathVariable("username") String username) {

		return fileService.findAllFileDetailsByUsername(username);
	}

	@GetMapping("/api/files/info/docname/{docname}")
	public List<FileDetailsDTO> findAllFIleDetailsByDocument(@PathVariable("docname") String docName) {
		return fileService.findAllFileDetailsByDocument(docName);
	}

	@GetMapping(value = "/api/files/zip/{username}", produces = "application/zip")
	public byte[] downloadFiles(HttpServletResponse response, @PathVariable("username") String username)
			throws IOException {
		response.setContentType("application/zip");
		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");
		LOG.info("# LOG # Initiated by [{}]: User downloaded archive with all files created#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return zipService.zipFiles(fileService.findAllFilesByUsername(username), username);
	}

	/**
	 * Returns csv file of users files and documents.
	 * 
	 * @param username
	 * @return
	 */
	@GetMapping(value = "/api/files/csv/{username}")
	public ResponseEntity<Resource> downloadCSV(@PathVariable String username) {
		LOG.info("# LOG # Initiated by [{}]: User generated and downloaded CSV file for all his files#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return fileService.generateCSV(username);
	}

	@DeleteMapping(path = "/api/files/delete/{UID}")
	public ResponseEntity<String> deleteFileByUID(@PathVariable("UID") String UID) throws IOException {
		fileService.deleteFileByUID(UID);
		LOG.info("# LOG # Initiated by [{}]: User deleted file with file UID: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}

}
