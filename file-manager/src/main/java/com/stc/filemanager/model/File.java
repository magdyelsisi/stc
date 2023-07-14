package com.stc.filemanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "FILE")
@Getter
@Setter
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Lob
	@Column(name = "binary_data", nullable = false)
	private byte[] binary;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ITEM_ID", nullable = false)
	private Item item;
}
