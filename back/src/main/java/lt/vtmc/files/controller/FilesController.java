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
import org.springframework.security.access.annotation.Secured;
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
	 * @url /api/file
	 * @method GET
	 * @param MultipartFile file, Document doc
	 * @return
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
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
	 * @url /api/files/{fileUID}
	 * @method GET
	 * @param fileName
	 * @return
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/api/files/{fileUID}")
	public ResponseEntity<Resource> downloadFileByFileName(@PathVariable("fileUID") String fileUID) {
		LOG.info("# LOG # Initiated by [{}]: User downloaded file with file UID: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), fileUID);
		return fileService.downloadFileByUID(fileUID);
	}
	
	/**
	 * Returns the details of files belonging to the user
	 * 
	 * @url /api/files/info/username/{username}
	 * @method GET
	 * @param username
	 * @return List<FileDetailsDTO>
	 */
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/api/files/info/username/{username}")
	public List<FileDetailsDTO> findAllFIleDetailsByUsername(@PathVariable("username") String username) {
		LOG.info("# LOG # Initiated by [{}]: User requested file details by username [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), username);
		return fileService.findAllFileDetailsByUsername(username);
	}

	/**
	 * Returns the details of files attached to document
	 * 
	 * @url /api/files/info/docname/{docname}
	 * @method GET
	 * @param documentName
	 * @return List<FileDetailsDTO>
	 */
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/api/files/info/docname/{docname}")
	public List<FileDetailsDTO> findAllFIleDetailsByDocument(@PathVariable("docname") String docName) {
		LOG.info("# LOG # Initiated by [{}]: User requested all file details by document [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), docName);
		return fileService.findAllFileDetailsByDocument(docName);
	}

	/**
	 * Creates a .zip file containing all files the user has created
	 * 
	 * @url /api/files/zip/{username}
	 * @method GET
	 * @param HttpServletResponse response, username
	 */
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
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
	 * @url /api/files/csv/{username}
	 * @method GET
	 * @param username
	 * @return
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "/api/files/csv/{username}")
	public ResponseEntity<Resource> downloadCSV(@PathVariable String username) {
		LOG.info("# LOG # Initiated by [{}]: User generated and downloaded CSV file for all his files#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return fileService.generateCSV(username);
	}
	
	/**
	 * Deletes a file from the system
	 * 
	 * @url /api/files/delete/{UID}
	 * @method DELETE
	 * @param UID
	 */
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@DeleteMapping(path = "/api/files/delete/{UID}")
	public ResponseEntity<String> deleteFileByUID(@PathVariable("UID") String UID) throws IOException {
		fileService.deleteFileByUID(UID);
		LOG.info("# LOG # Initiated by [{}]: User deleted file with file UID: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}

}
