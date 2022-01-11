package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private AuthenticationService authenticationService;
    private UserService userService;

    public FileService(FileMapper fileMapper, AuthenticationService authenticationService, UserService userService) {
        this.fileMapper = fileMapper;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public int storeFile(MultipartFile multipartFile) throws IOException {
        User user = userService.getUser(authenticationService.getLoggedInUserName());

        File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize(), user.getUserId(), multipartFile.getBytes());

        return fileMapper.insertFile(file);
    }

    public List<File> getFilesForUser() {
        User user = userService.getUser(authenticationService.getLoggedInUserName());

        return fileMapper.getFilesForUser(user.getUserId());
    }

    public ResponseEntity<Resource> downloadFile(Integer fileId) {
        File file = fileMapper.getFile(fileId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"").body(new ByteArrayResource(file.getFileData()));
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    //Checks if file exists
    public boolean doesFileExist(MultipartFile multipartFile) {
        List<File> userFiles = getFilesForUser();

        for (File file: userFiles) {
            if (file.getFileName().equals(multipartFile.getOriginalFilename())){
                return true;
            }
        }
        return false;
    }
}
