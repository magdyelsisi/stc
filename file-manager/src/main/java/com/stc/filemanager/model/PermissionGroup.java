package com.stc.filemanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PERMISSION_GROUP")
@Getter
@Setter
public class PermissionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "permissionGroup",cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();

    public void addPermission(Permission permission){
        permission.setPermissionGroup(this);
        permissions.add(permission);
    }
}
