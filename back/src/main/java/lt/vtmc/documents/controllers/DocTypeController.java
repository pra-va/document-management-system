package lt.vtmc.documents.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.documents.dto.CreateDocTypeCommand;
import lt.vtmc.documents.dto.DocTypeDetailsDTO;
import lt.vtmc.documents.dto.UpdateDocTypeCommand;
import lt.vtmc.documents.services.DocTypeService;
/**
 * Controller for managing Document Types.
 * 
 * @author VStoncius
 *
 */

@RestController
public class DocTypeController {

	@Autowired
	private DocTypeService docTypeService;
	
	/**
	 * Creates docType. Only system administrator should be able to
	 * access this method.
	 * 
	 * @url /api/createdoctype
	 * @method POST }
	 * @param user details
	 */
	
	@PostMapping(path = "/api/doct/create")
	public ResponseEntity<String> createDocType(@RequestBody CreateDocTypeCommand command){
		if (docTypeService.findDocTypeByName(command.getName()) == null) {
			docTypeService.createDocType(command.getName(), command.getCreating(), command.getApproving());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Failed to create doctype", HttpStatus.CONFLICT);
	}
	
	@GetMapping(path = "/api/doct/all")
	public List<DocTypeDetailsDTO> getAllDocTypes(){
		return docTypeService.getAllDocTypes();
	}
	
	@DeleteMapping(path = "/api/doct/delete/{name}")
	public ResponseEntity<String> deleteDocType(@PathVariable ("name") String name){
		docTypeService.deleteDocType(docTypeService.findDocTypeByName(name));
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}
	
	@GetMapping(path = "/api/doct/{name}/signs")
	public String[] findGroupsSigningDocType(@PathVariable ("name") String name){
		return docTypeService.findGroupsSigningDocType(name);
	}
	
	@GetMapping(path = "/api/doct/{name}/creates")
	public String[] findGroupsCreatingDocType(@PathVariable ("name") String name){
		return docTypeService.findGroupsCreatingDocType(name);
	}
	
	@PostMapping(path = "/api/doct/update/{name}")
	public ResponseEntity<String> updateDocTypeByGroupName(@RequestBody UpdateDocTypeCommand command,
			@PathVariable("name") String name) {
		if (docTypeService.findDocTypeByName(name) != null) {
			docTypeService.updateDocTypeDetails(command.getNewName(), name, command.getGroupsApproving(), command.getGroupsCreating());
			
//			LOG.info("# LOG # Initiated by [{}]: Group [{}] was updated #",
//					SecurityContextHolder.getContext().getAuthentication().getName(), name);

			return new ResponseEntity<String>("Updated succesfully", HttpStatus.ACCEPTED);
		}

//		LOG.info("# LOG # Initiated by [{}]: Group [{}] was NOT updated - [{}] was NOT found #",
//				SecurityContextHolder.getContext().getAuthentication().getName(), name, name);

		return new ResponseEntity<String>("No user found", HttpStatus.NOT_FOUND);
	}
}
