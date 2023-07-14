package com.stc.filemanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PERMISSION")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_EMAIL", nullable = false)
    private String userEmail;

    @Column(name = "PERMISSION_LEVEL", nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum level;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private PermissionGroup permissionGroup;

}
