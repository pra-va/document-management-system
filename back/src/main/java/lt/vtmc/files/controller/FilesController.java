package lt.vtmc.files.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.vtmc.files.service.FileService;
import lt.vtmc.user.controller.UserController;

@RestController
public class FilesController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private FileService fileUploadService;

	@PostMapping("/api/file")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		String returnValue = "start";
		try {
			LOG.info("File uploaded with file name: " + file.getOriginalFilename());
			fileUploadService.saveFile(file);
			returnValue = "success";
		} catch (Exception e) {
			LOG.error("Error saving file", e);
			returnValue = "error";
		}
		return returnValue;
	}

	@PostMapping(value = "/api/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadMultipleFiles(@RequestParam MultipartFile[] files) {
		System.out.println(files.toString());
		for (MultipartFile file : files) {
			try {
				LOG.info("Uploading file " + file.getOriginalFilename());
				fileUploadService.saveFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		String returnValue = "start";
//		try {
//			LOG.info("Multiple files uploaded . ");
//			fileUploadService.saveMultipleFiles(files);
//			returnValue = "success";
//		} catch (Exception e) {
//			LOG.error("Error saving file", e);
//			returnValue = "error";
//		}
//		return returnValue;
	}

	@GetMapping("/api/file/{fileName}")
	public ResponseEntity<Resource> downloadFileByFileName(String fileName) {
		return fileUploadService.downloadFileByName(fileName);
	}

}
