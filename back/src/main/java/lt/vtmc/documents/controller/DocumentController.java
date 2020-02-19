package lt.vtmc.documents.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.documents.dto.CreateDocumentCommand;
import lt.vtmc.documents.dto.DocumentDetailsDTO;
import lt.vtmc.documents.service.DocumentService;
import lt.vtmc.files.controller.FilesController;

/**
 * Controller for managing documents.
 * 
 * @author VStoncius
 *
 */
@RestController
public class DocumentController {

	@Autowired
	private DocumentService docService;

	@Autowired
	private FilesController filesControl;
	/**
	 * Creates document with specified fields.
	 * 
	 * @url /api/doc/create
	 * @method POST
	 * @param document details
	 */
	@PostMapping(path = "/api/doc/create")
	public ResponseEntity<String> createDocument(@RequestBody CreateDocumentCommand command, @RequestParam("Files") MultipartFile[] files) {
		if (docService.findDocumentByName(command.getName()) == null) {
			docService.createDocument(command.getName(), command.getAuthorUsername(), command.getDescription(), command.getDocType(), Instant.now().toString());
			if (files != null) {
				addFiles(command.getName(), files);
			}
//			LOG.info("# LOG # Initiated by [{}]: Group [{}] was created #",
//					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());
				return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
			}
//			LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT created #",
//					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());
		return new ResponseEntity<String>("Failed to create document", HttpStatus.CONFLICT);

	}

	@PostMapping
	public void addFiles( String docName, MultipartFile[] files){ // @PathVariable("docName"), ,@RequestParam("Files") 
		filesControl.uploadFiles(files, docService.findDocumentByName(docName));
	}
	
	@GetMapping(path = "/api/doc/all")
	public List<DocumentDetailsDTO> findAllDocuments() {
		return docService.findAll();
	}

	@GetMapping(path = "/api/doc/{name}")
	public DocumentDetailsDTO findDocument(@PathVariable("name") String name) {
		return new DocumentDetailsDTO(docService.findDocumentByName(name));
	}
	
	@GetMapping(path = "/api/doc/{name}/exists")
	public boolean checkDocument(@PathVariable("name") String name) {
		if (docService.findDocumentByName(name) != null) {
			return true;
		}
		return false;
	}
	
	@DeleteMapping(path = "/api/doc/delete/{name}")
	public ResponseEntity<String> deleteDocument(@PathVariable ("name") String name){
		docService.deleteDocument(docService.findDocumentByName(name));
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}
}
