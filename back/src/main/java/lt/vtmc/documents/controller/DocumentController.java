package lt.vtmc.documents.controller;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import lt.vtmc.paging.PagingData;
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
	 * @param command CreateDocumentCommand type JSON containing document name,
	 *                author username, description and document type.
	 * @return UID document unique identification number
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/create")
	public ResponseEntity<String> createDocument(@RequestBody CreateDocumentCommand command) {

		Document newDoc = docService.createDocument(command.getName(), command.getAuthorUsername(),
				command.getDescription(), command.getDocType(), Instant.now().toString());
		LOG.info("# LOG # Initiated by [{}]: Document: [{}] was created #",
				SecurityContextHolder.getContext().getAuthentication().getName(), command.getName());
		return new ResponseEntity<String>(newDoc.getUID(), HttpStatus.CREATED);
	}

	/**
	 * Uploads files to database and adds the to existing document
	 * 
	 * @param UID   unique identification number
	 * @param files files in MultipartFile[] format
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/api/doc/upload/{UID}")
	public void addFiles(@PathVariable("UID") String UID, @RequestParam("files") MultipartFile[] files) {
		LOG.info("# LOG # Initiated by [{}]: Files uploaded: [{}]#",
				SecurityContextHolder.getContext().getAuthentication().getName(), files);
		for (MultipartFile multipartFile : files) {
			filesControl.uploadFiles(multipartFile, docService.findDocumentByUID(UID));
		}
	}

	/**
	 * Finds one specific document by UID
	 * 
	 * @param UID unique identification number
	 * @return DocumentDetailsDTO a structurised version of document object without
	 *         recursions
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doc/{UID}")
	public DocumentDetailsDTO findDocument(@PathVariable("UID") String UID) {
		LOG.info("# LOG # Initiated by [{}]: Requested details of document UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new DocumentDetailsDTO(docService.findDocumentByUID(UID));
	}

	/**
	 * Deletes document and associated files
	 * 
	 * @param UID unique document id
	 * @return response entity of delete action status
	 */

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/api/doc/delete/{UID}")
	public ResponseEntity<String> deleteDocument(@PathVariable("UID") String UID) {
		docService.deleteDocument(docService.findDocumentByUID(UID));
		LOG.info("# LOG # Initiated by [{}]: Deleted document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}

	/**
	 * Changes document status to submitted
	 * 
	 * @param UID unique document id
	 * @return response entity of action status
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/submit/{UID}")
	public ResponseEntity<String> submitDocument(@PathVariable("UID") String UID) {
		docService.setStatusPateiktas(UID);
		LOG.info("# LOG # Initiated by [{}]: Submitted document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	/**
	 * Changes document status to approved
	 * 
	 * @param UID     unique document id
	 * @param command approve document command dto
	 * @return response entity of action status
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/approve/{UID}")
	public ResponseEntity<String> approveDocument(@PathVariable("UID") String UID,
			@RequestBody ApproveDocumentCommand command) {
		docService.setStatusPriimtas(UID, command.getUsername());
		LOG.info("# LOG # Initiated by [{}]: Approved document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	/**
	 * Changes document status to rejected
	 * 
	 * @param UID    unique document id
	 * @param reject document reject DTO
	 * @return response entity of action status
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/reject/{UID}")
	public ResponseEntity<String> rejectDocument(@PathVariable("UID") String UID,
			@RequestBody DocumentRejection reject) {
		docService.setStatusAtmestas(UID, reject.getUsername(), reject.getReasonToReject());
		LOG.info("# LOG # Initiated by [{}]: Rejected document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated succesfully", HttpStatus.OK);
	}

	/**
	 * Updates document details
	 * 
	 * @param UID     unique document id
	 * @param command containing new name, new description, new document type and
	 *                file list to remove
	 * @return response entity of action status
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/update{UID}")
	public ResponseEntity<String> updateDocument(@PathVariable("UID") String UID,
			@RequestBody UpdateDocumentCommand command) {
		docService.updateDocument(UID, command.getNewName(), command.getDescription(), command.getDocType(),
				command.getFilesToRemoveUID());
		LOG.info("# LOG # Initiated by [{}]: Updated document with UID: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), UID);
		return new ResponseEntity<String>("Updated", HttpStatus.OK);
	}

	/**
	 * Returns all submitted documents for the specified user
	 * 
	 * @param username   users unique username
	 * @param pagingData to set amount of items per page, search phrase and sorting
	 *                   order
	 * @return allSubmittedDocuments and paging info for the table
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/allsubmitted/{username}")
	public Map<String, Object> returnAllSubmittedDocuments(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all submitted documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnSubmitted(username, pagingData);
	}

	/**
	 * Returns all documents with status Created for the specified user
	 * 
	 * @param username   users unique username
	 * @param pagingData to set amount of items per page, search phrase and sorting
	 *                   order
	 * @return allCreatedDocuments and paging info for the table
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/allcreated/{username}")
	public Map<String, Object> returnAllCreatedDocuments(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all created documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnCreated(username, pagingData);
	}

	/**
	 * Returns all documents with status Accepted for the specified user
	 * 
	 * @param username   users unique username
	 * @param pagingData to set amount of items per page, search phrase and sorting
	 *                   order
	 * @return allAcceptedDocuments and paging info for the table
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/allaccepted/{username}")
	public Map<String, Object> returnAllAcceptedDocuments(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all accepted documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnAccepted(username, pagingData);
	}

	/**
	 * Returns all documents with status Rejected for the specified user
	 * 
	 * @param username   users unique username
	 * @param pagingData to set amount of items per page, search phrase and sorting
	 *                   order
	 * @return allRejectedDocuments and paging info for the table
	 */

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doc/allrejected/{username}")
	public Map<String, Object> returnAllRejectedDocuments(@PathVariable("username") String username,
			@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Requested a list of all rejected documents#",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docService.returnRejected(username, pagingData);
	}

	/**
	 * Remove document by document unique id if it belongs to a user that is sending
	 * this request.
	 * 
	 * @param uid documents unique identification number
	 * @return response status describing outcome
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@DeleteMapping("/api/doc/byUser/{uid}")
	public @ResponseBody ResponseEntity<String> removeDocByUser(@PathVariable String uid) {
		String requestedBy = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!requestedBy.equals(null)) {
			boolean isDocDeleted = docService.deleteDocumentRequestedByUser(uid, requestedBy);
			if (isDocDeleted) {
				LOG.info("# LOG # Initiated by [{}]: Deleted unsubmitted document with UID [{}]. #",
						SecurityContextHolder.getContext().getAuthentication().getName(), uid);
				return new ResponseEntity<String>("Document '" + uid + "' deleted.", HttpStatus.OK);
			} else {
				LOG.info(
						"# LOG # Initiated by [{}]: Failesd to delete document with UID [{}]. User either has not created this document or it has been already submitted. #",
						SecurityContextHolder.getContext().getAuthentication().getName(), uid);
				return new ResponseEntity<String>(
						"Unable to delete. User does not have requested document or status of the document is not 'CREATED'.",
						HttpStatus.NOT_FOUND);
			}
		} else {
			LOG.info(
					"# LOG # Initiated by [{}]: Unable to delete document with UID [{}]. User can not be identified. #",
					SecurityContextHolder.getContext().getAuthentication().getName(), uid);
			return new ResponseEntity<String>("Unable to delete. Failed to locate requesting user.",
					HttpStatus.UNAUTHORIZED);
		}
	}
}
