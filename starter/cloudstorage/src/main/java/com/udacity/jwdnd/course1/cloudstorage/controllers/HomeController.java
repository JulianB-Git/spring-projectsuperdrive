package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialsService credentialsService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, CredentialsService credentialsService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Notes notes, Credentials credentials, EncryptionService encryptionService, Model model) {
        model.addAttribute("notesList", noteService.getAllNotesForUser());
        model.addAttribute("credentialsList", credentialsService.getCredentialsForUser());
        model.addAttribute("fileList", fileService.getFilesForUser());
        return "home";
    }

}
