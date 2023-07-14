package com.stc.filemanager.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stc.filemanager.model.*;
import com.stc.filemanager.repository.ItemRepository;
import com.stc.filemanager.repository.PermissionGroupRepository;
import com.stc.filemanager.validation.UserPermissionWrapper;

@Service
public class PermissionService {

	private final PermissionGroupRepository permissionGroupRepository;

	private final ItemRepository itemRepository;

	@Autowired
	public PermissionService(PermissionGroupRepository permissionGroupRepository, ItemRepository itemRepository) {
		this.permissionGroupRepository = permissionGroupRepository;
		this.itemRepository = itemRepository;
	}

	public PermissionGroup createDefaultPermissions() {
		PermissionGroup permissionGroup = new PermissionGroup();
		permissionGroup.setName("ADMIN");

		Permission viewPermission = new Permission();
		viewPermission.setLevel(PermissionLevelEnum.VIEW);
		viewPermission.setUserEmail("magdy.aelsisi@yahoo.com");

		Permission editPermission = new Permission();
		editPermission.setLevel(PermissionLevelEnum.EDIT);
		editPermission.setUserEmail("magdy@gmail.com");

		permissionGroup.addPermission(viewPermission);
		permissionGroup.addPermission(editPermission);
		permissionGroup = permissionGroupRepository.save(permissionGroup);
		return permissionGroup;
	}

	public boolean isAuthorized(UserPermissionWrapper itemValidatorWrapper) {
		Optional<Item> itemOptional = itemRepository.findByNameAndType(itemValidatorWrapper.getParentItemName(),
				itemValidatorWrapper.getItemType());
		if (itemOptional.isPresent()) {
			Item item = itemOptional.get();
			PermissionGroup permissionGroup;
			if (item.getType().equals(ItemTypeEnum.FOLDER)) {
				//if the item is folder, get space permission group
				permissionGroup = item.getParentItem().getPermissionGroup();
			} else {
				permissionGroup = item.getPermissionGroup();
			}
			Optional<Permission> optional = permissionGroup.getPermissions().stream()
					.filter(permission -> itemValidatorWrapper.getPermissions().contains(permission.getLevel())
							&& permission.getUserEmail().equals(itemValidatorWrapper.getUserEmail()))
					.findFirst();
			return optional.isPresent();

		} else {
			throw new EntityNotFoundException(
					"No " + itemValidatorWrapper.getItemType() + " found with name [" + itemValidatorWrapper.getParentItemName() + "]");
		}
	}
}
