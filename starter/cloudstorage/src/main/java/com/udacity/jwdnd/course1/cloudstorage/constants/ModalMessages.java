package com.udacity.jwdnd.course1.cloudstorage.constants;

public interface ModalMessages {
    String ERROR_DATABASE_EXCEPTION_NOTE = "Description field allows a maximum of 1000 characters.";
    String ERROR_DATABASE_EXCEPTION_CREDENTIALS = "Text fields allows a maximum of 1000 characters.";

    String ERROR_FILE_EXISTS = "Filename already exists.";
    String ERROR_FILE_NOT_FOUND = "Please select a file first before uploading.";
    String ERROR_FILE_EXCEEDS_LIMIT = "The selected file exceeds the maximum upload size of 3mb.";

    String ERROR_NOTE_ALREADY_EXISTS = "A note with the entered title already exists.";
    String ERROR_CREDENTIALS_ALREADY_EXISTS = "Credentials for this URL and Username already exist.";

    String ERROR_USER_EXISTS = "The username already exists.";
    String ERROR_SIGNUP = "There was an error signing you up. Please try again.";

}
