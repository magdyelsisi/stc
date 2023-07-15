package com.stc.filemanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.stc.filemanager.model.Item;
import com.stc.filemanager.service.ItemService;
import com.stc.filemanager.util.Constants;

@RestController
@RequestMapping("/item")
@Validated
public class ItemController {

	private final ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping("/create-space")
	public ResponseEntity<Item> createSpace(@Valid @RequestBody Item item) {
		Item savedItem = itemService.createSpaceWithDefaultPermissions(item);
		return new ResponseEntity<>(savedItem, HttpStatus.CREATED);

	}

	@PostMapping("/create-folder")
	public ResponseEntity<Item> createFolder(@Valid @RequestBody Item item, @RequestHeader(Constants.EMAIL_HEADER) String userEmail) {
		Item savedItem = itemService.createFolder(item, userEmail);
		return new ResponseEntity<>(savedItem, HttpStatus.CREATED);

	}

}
