package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.ModalMessages;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileUploadController {

    private FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        boolean success = true;

        //See if a file was selected
        if (Objects.requireNonNull(fileUpload.getOriginalFilename()).isEmpty()) {
            model.addAttribute("uploadFailure", ModalMessages.ERROR_FILE_NOT_FOUND);
            return "result";
        }

        //Checks if filename already exists
        if (fileService.doesFileExist(fileUpload)) {
            model.addAttribute("uploadFailure", ModalMessages.ERROR_FILE_EXISTS);
            return "result";
        }

        int rowsUpdated = fileService.storeFile(fileUpload);

        //Checks if the database operation returned the number of rows changed
        if(rowsUpdated == 0) {
            success = false;
        }
        model.addAttribute("success", success);

        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(Integer fileId) {
        return fileService.downloadFile(fileId);
    }

    @GetMapping("/delete")
    public String deleteFile(Integer fileId, Model model) {
        boolean success = true;
        int rowsUpdated;

        rowsUpdated = fileService.deleteFile(fileId);

        //Checks if the database operation returned the number of rows changed
        if(rowsUpdated < 1) {
            model.addAttribute("success", false);
        }else {
            model.addAttribute("success", success);
        }

        return "result";
    }

}
