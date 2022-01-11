package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.ModalMessages;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String addNewNote(Notes notes, Model model) {
        boolean success = true;
        int rowsUpdated;

        //Check if the form has an existing note ID then update else add new
        if (notes.getNoteId() != null){
            rowsUpdated = noteService.updateNote(notes);
        } else{
            //Chekcs if the note already exists
            if (noteService.doesNoteExist(notes)) {
                model.addAttribute("error", ModalMessages.ERROR_NOTE_ALREADY_EXISTS);
                return "result";
            }

            rowsUpdated = noteService.addNewNote(notes);
        }

        //Checks if the database operation returned the number of rows changed or an exception
        if(rowsUpdated == 0) {
            model.addAttribute("success", false);
        }else if(rowsUpdated == -1) {
            model.addAttribute("error", ModalMessages.ERROR_DATABASE_EXCEPTION_NOTE);
        } else {
            model.addAttribute("success", success);
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(Integer noteId, Model model) {
        boolean success = true;
        int rowsUpdated;

        rowsUpdated = noteService.deleteNote(noteId);

        //Checks if the database operation returned the number of rows changed
        if(rowsUpdated < 1) {
            model.addAttribute("success", false);
        }else {
            model.addAttribute("success", success);
        }

        return "result";
    }
}
