package lt.vtmc.documents.controller;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.documents.dto.ApproveDocumentCommand;
import lt.vtmc.documents.dto.CreateDocumentCommand;
import lt.vtmc.documents.dto.DocumentDetailsDTO;
import lt.vtmc.documents.dto.DocumentRejection;
import lt.vtmc.documents.dto.UpdateDocumentCommand;
import lt.vtmc.documents.model.Document;
import lt.vtmc.documents.service.DocumentService;
import lt.vtmc.files.controller.FilesController;
import lt.vtmc.user.controller.UserController;

/**
 * Controller for managing documents.
 * 
 * @author VStoncius
 *
 */
@RestController
public class DocumentController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

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
	public ResponseEntity<String> createDocument(@RequestBody CreateDocumentCommand command) {

		Document newDoc = docService.createDocument(command.getName(), command.getAuthorUsername(),
				command.getDescription(), command.getDocType(), Instant.now().toString());
		LOG.info("# LOG # Initiated by [{}]: Document: [{}] was created #",
				SecurityContextHolder.getContext().getAuthentication().getName(), command.getName());
		return new ResponseEntity<String>(newDoc.getUID(), HttpStatus.CREATED);
	}

	@PostMapping("/api/doc/upload/{UID}")
	public void addFiles(@PathVariable("UID") String UID, @RequestParam("files") MultipartFile[] files) {
		LOG.info("# LOG # Initiated by [{}]: Files uploaded: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), files);
		for (MultipartFile multipartFile : files) {
			filesControl.uploadFiles(multipartFile, docService.findDocumentByUID(UID));
		}
	}

	@GetMapping(path = "/api/doc/all")
	public List<DocumentDetailsDTO> findAllDocuments() {
		return docService.findAll();
	}

	@GetMapping(path = "/api/doc/{UID}")
	public DocumentDetailsDTO findDocument(@PathVariable("UID") String UID) {
		LOG.info("# LOG # Initiated by [{}]: Requested details of document UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new DocumentDetailsDTO(docService.findDocumentByUID(UID));
	}

	@DeleteMapping(path = "/api/doc/delete/{UID}")
	public ResponseEntity<String> deleteDocument(@PathVariable("UID") String UID) {
		docService.deleteDocument(docService.findDocumentByUID(UID));
		LOG.info("# LOG # Initiated by [{}]: Deleted document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}

	@PostMapping(path = "/api/doc/submit/{UID}")
	public ResponseEntity<String> submitDocument(@PathVariable("UID") String UID) {
		docService.setStatusPateiktas(UID);
		LOG.info("# LOG # Initiated by [{}]: Submitted document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	@PostMapping(path = "/api/doc/approve/{UID}")
	public ResponseEntity<String> approveDocument(@PathVariable("UID") String UID,
			@RequestBody ApproveDocumentCommand command) {
		docService.setStatusPriimtas(UID, command.getUsername());
		LOG.info("# LOG # Initiated by [{}]: Approved document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	@PostMapping(path = "/api/doc/reject/{UID}")
	public ResponseEntity<String> rejectDocument(@PathVariable("UID") String UID,
			@RequestBody DocumentRejection reject) {
		docService.setStatusAtmestas(UID, reject.getUsername(), reject.getReasonToReject());
		LOG.info("# LOG # Initiated by [{}]: Rejected document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	@PostMapping(path = "/api/doc/update{UID}")
	public ResponseEntity<String> updateDocument(@PathVariable("UID") String UID,
			@RequestBody UpdateDocumentCommand command) {
		docService.updateDocument(UID, command.getNewName(), command.getDescription(), command.getDocType(),
				command.getFilesToRemoveUID());
		LOG.info("# LOG # Initiated by [{}]: Updated document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated", HttpStatus.OK);
	}

	@GetMapping(path = "/api/doc/allsubmitted/{username}")
	public List<DocumentDetailsDTO> returnAllSubmittedDocuments(@PathVariable("username") String username) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all submitted documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnSubmitted(username);
	}

	@GetMapping(path = "/api/doc/allcreated/{username}")
	public List<DocumentDetailsDTO> returnAllCreatedDocuments(@PathVariable("username") String username) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all created documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnCreated(username);
	}

	@GetMapping(path = "/api/doc/allaccepted/{username}")
	public List<DocumentDetailsDTO> returnAllAcceptedDocuments(@PathVariable("username") String username) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all accepted documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnAccepted(username);
	}

	@GetMapping(path = "/api/doc/allrejected/{username}")
	public List<DocumentDetailsDTO> returnAllRejectedDocuments(@PathVariable("username") String username) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all rejected documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnRejected(username);
	}
}
