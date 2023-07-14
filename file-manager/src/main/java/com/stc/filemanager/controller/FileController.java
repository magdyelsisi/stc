package com.stc.filemanager.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.stc.filemanager.model.File;
import com.stc.filemanager.model.Item;
import com.stc.filemanager.model.PermissionGroup;
import com.stc.filemanager.service.FileService;
import com.stc.filemanager.utl.Constants;

@RestController
@RequestMapping("/file")
public class FileController {

	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/create")
	public ResponseEntity<File> createFile(@RequestParam("file") MultipartFile file, @RequestParam(name = "folderName") String folderName,
			@RequestHeader(Constants.EMAIL_HEADER) String userEmail) throws IOException {
		File savedFile = fileService.createFile(file, folderName, userEmail);
		return new ResponseEntity<>(savedFile, HttpStatus.CREATED);

	}

	@GetMapping("/download/{spaceName}/{folderName}/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String spaceName, @PathVariable String folderName, @PathVariable String fileName,
			@RequestHeader(Constants.EMAIL_HEADER) String userEmail) {
		File file = fileService.load(spaceName, folderName, fileName, userEmail);
		ByteArrayResource resource = new ByteArrayResource(file.getBinary(), file.getItem().getName());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
	}

	@QueryMapping
	public File fileBySpaceAndFolderAndName(@Argument String spaceName, @Argument String folderName, @Argument String fileName) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String userEmail = attributes.getRequest().getHeader(Constants.EMAIL_HEADER);
		return fileService.load(spaceName, folderName, fileName, userEmail);
	}

	@SchemaMapping(typeName = "Item", field = "permissionGroup")
	public PermissionGroup getItemPermissionGroup(Item item) {
		if (item.getPermissionGroup() != null) {
			return item.getPermissionGroup();
		} else {
			return getItemPermissionGroup(item.getParentItem());
		}
	}
}
