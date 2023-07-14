package com.stc.filemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private ItemTypeEnum type;

	@NotBlank
	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "PERMISSION_GROUP_ID")
	@JsonIgnore
	private PermissionGroup permissionGroup;

	@ManyToOne
	@JoinColumn(name = "PARENT_ITEM_ID")
	private Item parentItem;

	@Transient
	private String parentName;

}
