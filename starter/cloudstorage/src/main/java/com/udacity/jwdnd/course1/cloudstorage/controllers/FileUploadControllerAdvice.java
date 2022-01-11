package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.constants.ModalMessages;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FileUploadControllerAdvice {
    //Checks if the upload size is permitted
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeException() {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.getModel().put("uploadFailure", ModalMessages.ERROR_FILE_EXCEEDS_LIMIT);
        return modelAndView;
    }
}
