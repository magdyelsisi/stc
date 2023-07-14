package com.stc.filemanager.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stc.filemanager.exception.AuthorizationException;
import com.stc.filemanager.exception.ValidationException;
import com.stc.filemanager.model.File;
import com.stc.filemanager.model.Item;
import com.stc.filemanager.model.ItemTypeEnum;
import com.stc.filemanager.model.PermissionLevelEnum;
import com.stc.filemanager.repository.FileRepository;
import com.stc.filemanager.repository.ItemRepository;
import com.stc.filemanager.validation.UserPermissionWrapper;

@Service
public class FileService {

	private final ItemRepository itemRepository;
	private final PermissionService permissionService;
	private final FileRepository fileRepository;

	public FileService(ItemRepository itemRepository, PermissionService permissionService, FileRepository fileRepository) {
		this.itemRepository = itemRepository;
		this.permissionService = permissionService;
		this.fileRepository = fileRepository;
	}

	public File createFile(MultipartFile multipartFile, String folderName, String userEmail) throws IOException {
		UserPermissionWrapper wrapper = new UserPermissionWrapper();
		wrapper.setPermissions(Collections.singletonList(PermissionLevelEnum.EDIT));
		wrapper.setItemType(ItemTypeEnum.FOLDER);
		wrapper.setParentItemName(folderName);
		wrapper.setUserEmail(userEmail);
		if (permissionService.isAuthorized(wrapper)) {
			String originalFilename = multipartFile.getOriginalFilename();
			long count = itemRepository.countByNameAndTypeAndParentItemName(originalFilename, ItemTypeEnum.FILE, folderName);
			if (count > 0) {
				throw new ValidationException(
						"Another folder found with name [" + originalFilename + "] under [" + folderName + "] folder");
			}

			File file = new File();
			file.setBinary(multipartFile.getBytes());
			Item item = new Item();
			item.setName(originalFilename);
			item.setType(ItemTypeEnum.FILE);
			item.setParentItem(itemRepository.findByName(folderName).get());
			file.setItem(item);
			return fileRepository.save(file);
		} else {
			throw new AuthorizationException(
					"You're not authorized to " + PermissionLevelEnum.EDIT + " " + ItemTypeEnum.FOLDER + " [" + folderName + "]");
		}
	}

	public File load(String spaceName, String folderName, String fileName, String email) {

		UserPermissionWrapper wrapper = new UserPermissionWrapper();
		wrapper.setPermissions(Arrays.asList(PermissionLevelEnum.VIEW, PermissionLevelEnum.EDIT));
		wrapper.setItemType(ItemTypeEnum.FOLDER);
		wrapper.setParentItemName(folderName);
		wrapper.setUserEmail(email);

		if (permissionService.isAuthorized(wrapper)) {
			Optional<File> optional = fileRepository.findByNameAndFolderNameAndSpaceName(fileName, folderName, spaceName);
			if (optional.isPresent()) {
				return optional.get();
			} else {
				throw new EntityNotFoundException(
						"No file found with name [" + fileName + "] under folder [" + folderName + "] in space [" + spaceName + "]");
			}

		} else {
			throw new AuthorizationException(
					"You're not authorized to " + PermissionLevelEnum.EDIT + " " + ItemTypeEnum.FOLDER + " [" + folderName + "]");
		}

	}
}
