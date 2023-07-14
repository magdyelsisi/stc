package com.stc.filemanager.validation;

import com.stc.filemanager.model.ItemTypeEnum;
import com.stc.filemanager.model.PermissionLevelEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to validate user permission to read/edit items
 */
@Getter
@Setter
public class UserPermissionWrapper {

    private List<PermissionLevelEnum> permissions;
    private String userEmail;
    private ItemTypeEnum itemType;
    private String parentItemName;

}
