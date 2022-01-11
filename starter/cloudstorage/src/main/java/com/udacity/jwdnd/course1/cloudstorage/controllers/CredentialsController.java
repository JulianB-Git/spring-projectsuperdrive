package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.ModalMessages;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public String insertCredentials(Credentials credentials, Model model) {
        boolean success = true;
        int rowsUpdated;

        //Check if the form has an existing credential ID then update else add new
        if (credentials.getCredentialsID() != null){
            rowsUpdated = credentialsService.updateCredentials(credentials);
        } else{
            //Checks if the credentials are already saved to avoid duplicates
            if (credentialsService.credentialsExist(credentials)){
                model.addAttribute("error", ModalMessages.ERROR_CREDENTIALS_ALREADY_EXISTS);
                return "result";
            }

            rowsUpdated = credentialsService.insertCredentials(credentials);
        }

        //Checks if the database operation returned the number of rows changed
        if(rowsUpdated < 1) {
            model.addAttribute("success", false);
        } else {
            model.addAttribute("success", success);
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredentials(Integer credentialsID, Model model) {
        boolean success = true;
        int rowUpdated = credentialsService.deleteCredentials(credentialsID);

        //Checks if the database operation returned the number of rows changed
        if(rowUpdated < 1) {
            model.addAttribute("success", false);
        } else {
            model.addAttribute("success", success);
        }

        return "result";
    }

}
