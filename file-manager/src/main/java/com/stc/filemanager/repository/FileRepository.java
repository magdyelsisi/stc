package com.stc.filemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stc.filemanager.model.File;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, Long> {

	@Query("select file from File file join file.item item join item.parentItem folder join folder.parentItem space where item.name=:itemName and folder.name=:folderName and space.name=:spaceName")
	Optional<File> findByNameAndFolderNameAndSpaceName(String itemName, String folderName, String spaceName);

}
