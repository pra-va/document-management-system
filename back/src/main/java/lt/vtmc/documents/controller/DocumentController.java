package lt.vtmc.documents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.documents.dto.CreateDocumentCommand;
import lt.vtmc.documents.service.DocumentService;

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
	
	/**
	 * Creates document with specified fields. 
	 * 
	 * @url /api/doc/create
	 * @method POST
	 * @param document details
	 */
	@PostMapping(path = "/api/doc/create")
	public ResponseEntity<String> createDocument(@RequestBody CreateDocumentCommand command) {
		if (docService.findDocumentByName(command.getName()) == null) {
			docService.createDocument(command.getName(), command.getAuthorUsername(), command.getDescription(), command.getDocType(), command.getCurrentTime());
//			LOG.info("# LOG # Initiated by [{}]: Group [{}] was created #",
//					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else

//			LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT created #",
//					SecurityContextHolder.getContext().getAuthentication().getName(), command.getGroupName());
		return new ResponseEntity<String>("Failed to create group", HttpStatus.CONFLICT);
	}

	@GetMapping(path = "/api/doc/all")
	public String[] findAllDocuments(){
		return docService.findAll();
	}
}
