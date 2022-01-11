package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private AuthenticationService authenticationService;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, AuthenticationService authenticationService, UserService userService) {
        this.noteMapper = noteMapper;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    //Gets all notes for the current user
    public List<Notes> getAllNotesForUser() {
        User user = userService.getUser(authenticationService.getLoggedInUserName());
        return noteMapper.getAllNotesForUser(user.getUserId());
    }

    public int addNewNote(Notes notes) {
        User user = userService.getUser(authenticationService.getLoggedInUserName());
        int rowUpdated = 0;

        notes.setUserID(user.getUserId());

        try {
            rowUpdated =noteMapper.addNewNote(notes);
        } catch (Exception e) {
            return -1;
        }

        return rowUpdated;
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    public int updateNote(Notes notes) {
        int rowUpdated = 0;
        try{
            rowUpdated = noteMapper.updateNote(notes);
        } catch (Exception e){
            return -1;
        }
        return rowUpdated;
    }

    //Checks if the user already has a note saved to avoid duplicates
    public boolean doesNoteExist(Notes notes) {
        List<Notes> userNotes = getAllNotesForUser();

        for (Notes note: userNotes) {
            if (note.getNoteTitle().equalsIgnoreCase(notes.getNoteTitle())) {
                return true;
            }
        }

        return false;
    }

}
