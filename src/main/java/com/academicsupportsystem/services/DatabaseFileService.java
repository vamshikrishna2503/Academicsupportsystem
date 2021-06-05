package com.academicsupportsystem.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.academicsupportsystem.models.DatabaseFile;
import com.academicsupportsystem.models.User;
import com.academicsupportsystem.repositories.DatabaseFileRepository;
import com.academicsupportsystem.utils.FileNotFoundException;
import com.academicsupportsystem.utils.FileStorageException;


@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file, String fileName, String type, User user) {
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, type, file.getBytes(), user);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).get();   
    }
    
    public List<DatabaseFile> getAllFiles(){
    	return (List<DatabaseFile>) dbFileRepository.findAll();
    }
}