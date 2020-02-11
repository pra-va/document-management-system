package lt.vtmc.documents.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.documents.dto.CreateDocTypeCommand;
import lt.vtmc.documents.model.DocType;
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
	
	@PostMapping(path = "/api/createdoctype")
	public ResponseEntity<String> createDocType(@RequestBody CreateDocTypeCommand command){
		if (docTypeService.findGroupByName(command.getName()) == null) {
			docTypeService.createDocType(command.getName(), command.getCreating(), command.getApproving());
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Failed to create doctype", HttpStatus.CONFLICT);
	}
	
	@GetMapping(path = "/api/alldoctypes")
	public List<DocType> getAllDocTypes(){
		List<DocType> list = docTypeService.getAllDocTypes();
		return list;
	}
	
	@DeleteMapping(path = "/api/doctypes/delete/{name}")
	public ResponseEntity<String> deleteDocType(@PathVariable ("name") String name){
		docTypeService.deleteDocType(name);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}
}
