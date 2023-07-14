package com.stc.filemanager.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stc.filemanager.exception.AuthorizationException;
import com.stc.filemanager.exception.ValidationException;
import com.stc.filemanager.model.Item;
import com.stc.filemanager.model.ItemTypeEnum;
import com.stc.filemanager.model.PermissionGroup;
import com.stc.filemanager.model.PermissionLevelEnum;
import com.stc.filemanager.repository.ItemRepository;
import com.stc.filemanager.validation.UserPermissionWrapper;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final PermissionService permissionService;

	@Autowired
	public ItemService(ItemRepository itemRepository, PermissionService permissionService) {
		this.itemRepository = itemRepository;
		this.permissionService = permissionService;
	}

	@Transactional
	public Item createSpaceWithDefaultPermissions(Item item) {
		long count = itemRepository.countByNameAndType(item.getName(), ItemTypeEnum.SPACE);
		if (count > 0) {
			throw new ValidationException("Another space found with name [" + item.getName() + "]");
		}
		PermissionGroup defaultPermissions = permissionService.createDefaultPermissions();
		item.setPermissionGroup(defaultPermissions);
		return itemRepository.save(item);

	}

	public Item createFolder(Item item, String userEmail) {

		if (item.getParentName() == null || item.getParentName().isEmpty()) {
			throw new ValidationException("Missing space name");
		}
		long count = itemRepository.countByNameAndTypeAndParentItemName(item.getName(), ItemTypeEnum.FOLDER, item.getParentName());
		if (count > 0) {
			throw new ValidationException(
					"Another folder found with name [" + item.getName() + "] under [" + item.getParentName() + "] space");
		}

		UserPermissionWrapper wrapper = new UserPermissionWrapper();
		wrapper.setPermissions(Collections.singletonList(PermissionLevelEnum.EDIT));
		wrapper.setItemType(ItemTypeEnum.SPACE);
		wrapper.setParentItemName(item.getParentName());
		wrapper.setUserEmail(userEmail);

		if (permissionService.isAuthorized(wrapper)) {
			if (item.getType() != null && !item.getType().equals(ItemTypeEnum.FOLDER)) {
				throw new ValidationException("Invalid item type");
			} else if (item.getType() == null) {
				item.setType(ItemTypeEnum.FOLDER);
			}
			item.setParentItem(itemRepository.findByName(item.getParentName()).get());
			return itemRepository.save(item);
		} else {
			throw new AuthorizationException("You're not authorized to " + PermissionLevelEnum.EDIT + " " + item.getParentName());
		}

	}

}
