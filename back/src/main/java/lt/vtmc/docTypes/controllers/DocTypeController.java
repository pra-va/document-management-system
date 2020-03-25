package lt.vtmc.docTypes.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.docTypes.dto.CreateDocTypeCommand;
import lt.vtmc.docTypes.dto.DocTypeDetailsDTO;
import lt.vtmc.docTypes.dto.UpdateDocTypeCommand;
import lt.vtmc.docTypes.services.DocTypeService;
import lt.vtmc.paging.PagingData;
import lt.vtmc.user.controller.UserController;

/**
 * Controller for managing Document Types.
 * 
 * @author VStoncius
 *
 */

@RestController
public class DocTypeController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private DocTypeService docTypeService;

	/**
	 * Creates docType. Only system administrator should be able to access this
	 * method.
	 * 
	 * @param command with doc type details
	 * @return response entity with status and message
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/create")
	public ResponseEntity<String> createDocType(@RequestBody CreateDocTypeCommand command) {
		if (docTypeService.findDocTypeByName(command.getName()) == null) {
			docTypeService.createDocType(command.getName(), command.getCreating(), command.getApproving());
			LOG.info("# LOG # Initiated by [{}]: Created document type: [{}] #",
					SecurityContextHolder.getContext().getAuthentication().getName(), command.getName());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<String>("Failed to create doctype", HttpStatus.CONFLICT);
	}

	/**
	 * Controller method that will return all document types.
	 * 
	 * @param pagingData to set amount of items per page, search phrase, sorting
	 *                   order
	 * @return allDocumentTypes and paging info for table.
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/all")
	public Map<String, Object> getAllDocTypes(@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved all document types. #",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docTypeService.retrieveAllDocTypes(pagingData);
	}

	/**
	 * Controller method to get all document types without groups.
	 * 
	 * @param pagingData to set amount of items per page, search phrase, sorting
	 *                   order
	 * @return allDocumentTypes and paging info for table.
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/all/nogroups")
	public Map<String, Object> getAllDocTypesNoGroups(@RequestBody PagingData pagingData) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved all document types without groups. #",
				SecurityContextHolder.getContext().getAuthentication().getName());
		return docTypeService.retrieveAllDocTypesNoGroups(pagingData);
	}

	/**
	 * Controller method to get approving groups by user name provided in
	 * parameters.
	 * 
	 * @param docTypeName document type name
	 * @return List of groups that approve doc type provided in parameters.
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/groups/approving")
	public List<String> getGroupsApproving(String docTypeName) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved all approving groups for document type [{}]. #",
				SecurityContextHolder.getContext().getAuthentication().getName(), docTypeName);
		return docTypeService.getGroupsApproving(docTypeName);
	}

	/**
	 * Controller method to get creating groups by user name provided in parameters.
	 * 
	 * @param docTypeName document type name
	 * @return List of groups that approve doc type provided in parameters.
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/groups/creating")
	public List<String> getGroupsCreating(String docTypeName) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved all creating groups for document type [{}]. #",
				SecurityContextHolder.getContext().getAuthentication().getName(), docTypeName);
		return docTypeService.getGroupsCreating(docTypeName);
	}

	/**
	 * Controller method to find document type by provided name. Name has to be
	 * exact.
	 * 
	 * @param name of document type
	 * @return DocType
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}")
	public DocTypeDetailsDTO findDocTypeByName(@PathVariable("name") String name) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved document type [{}]. #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);
		return new DocTypeDetailsDTO(docTypeService.findDocTypeByName(name));
	}

	/**
	 * Controller method to delete docType by provided name.
	 * 
	 * @param name of document type
	 * @return ResponseEntity
	 */
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/api/doct/delete/{name}")
	public ResponseEntity<String> deleteDocType(@PathVariable("name") String name) {
		docTypeService.deleteDocType(docTypeService.findDocTypeByName(name));
		LOG.info("# LOG # Initiated by [{}]: Deleted document type: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}

	/**
	 * Controller method that will find groups that can sign document type by
	 * provided name.
	 * 
	 * @param name of document type
	 * @return List of groups that can sign specific doc type
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/signs")
	public String[] findGroupsSigningDocType(@PathVariable("name") String name) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved groups that can sign document type [{}]. #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);
		return docTypeService.findGroupsSigningDocType(name);
	}

	/**
	 * Controller method that will return list of groups creating doc type.
	 * 
	 * @param name of document type
	 * @return list of groups that are able to create doc
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/creates")
	public String[] findGroupsCreatingDocType(@PathVariable("name") String name) {
		LOG.info("# LOG # Initiated by [{}]: Retrieved groups that can create document type [{}]. #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);
		return docTypeService.findGroupsCreatingDocType(name);
	}

	/**
	 * Controller method to check if doc type with provided name exists
	 * 
	 * @param name of document type
	 * @return true if exists, false otherwise
	 */
	@Secured({ "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/exists")
	public boolean docTypeExists(@PathVariable("name") String name) {
		if (docTypeService.findDocTypeByName(name) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Controller method to update document type by name.
	 * 
	 * @param command with updated document type information
	 * @param name    of document type to update
	 * @return ResponseEntity
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/update/{name}")
	public ResponseEntity<String> updateDocTypeByName(@RequestBody UpdateDocTypeCommand command,
			@PathVariable("name") String name) {
		if (docTypeService.findDocTypeByName(name) != null) {
			docTypeService.updateDocTypeDetails(command.getNewName(), name, command.getGroupsApproving(),
					command.getGroupsCreating());

			LOG.info("# LOG # Initiated by [{}]: Updated document type: [{}] #",
					SecurityContextHolder.getContext().getAuthentication().getName(), name);

			return new ResponseEntity<String>("Updated succesfully", HttpStatus.ACCEPTED);
		}

		LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT updated - [{}] was NOT found #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name, name);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}
}
