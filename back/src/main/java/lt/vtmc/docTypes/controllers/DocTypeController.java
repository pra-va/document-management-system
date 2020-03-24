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
	 * @url /api/createdoctype
	 * @method POST }
	 * @param user details
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

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/all")
	public Map<String, Object> getAllDocTypes(@RequestBody PagingData pagingData) {
		return docTypeService.retrieveAllDocTypes(pagingData);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/all/nogroups")
	public Map<String, Object> getAllDocTypesNoGroups(@RequestBody PagingData pagingData) {
		return docTypeService.retrieveAllDocTypesNoGroups(pagingData);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/groups/approving")
	public List<String> getGroupsApproving(String docTypeName) {
		return docTypeService.getGroupsApproving(docTypeName);
	}

//	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
//	@GetMapping(path = "/api/doct/names")
//	public List<String> getDocTypeNames() {
//		return docTypeService.g;
//	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/groups/creating")
	public List<String> getGroupsCreating(String docTypeName) {
		return docTypeService.getGroupsCreating(docTypeName);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}")
	public DocTypeDetailsDTO findDocTypeByName(@PathVariable("name") String name) {
		return new DocTypeDetailsDTO(docTypeService.findDocTypeByName(name));
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping(path = "/api/doct/delete/{name}")
	public ResponseEntity<String> deleteDocType(@PathVariable("name") String name) {
		docTypeService.deleteDocType(docTypeService.findDocTypeByName(name));
		LOG.info("# LOG # Initiated by [{}]: Deleted document type: [{}] #",
				SecurityContextHolder.getContext().getAuthentication().getName(), name);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/signs")
	public String[] findGroupsSigningDocType(@PathVariable("name") String name) {
		return docTypeService.findGroupsSigningDocType(name);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/creates")
	public String[] findGroupsCreatingDocType(@PathVariable("name") String name) {
		return docTypeService.findGroupsCreatingDocType(name);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping(path = "/api/doct/{name}/exists")
	public boolean docTypeExists(@PathVariable("name") String name) {
		if (docTypeService.findDocTypeByName(name) != null) {
			return true;
		}
		return false;
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping(path = "/api/doct/update/{name}")
	public ResponseEntity<String> updateDocTypeByGroupName(@RequestBody UpdateDocTypeCommand command,
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
