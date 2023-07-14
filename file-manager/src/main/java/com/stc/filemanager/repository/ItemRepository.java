package com.stc.filemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stc.filemanager.model.Item;
import com.stc.filemanager.model.ItemTypeEnum;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByNameAndType(String name, ItemTypeEnum type);

	Optional<Item> findByName(String name);

	long countByNameAndType(String name, ItemTypeEnum type);

	long countByNameAndTypeAndParentItemName(String name, ItemTypeEnum type,String parentName);

}
