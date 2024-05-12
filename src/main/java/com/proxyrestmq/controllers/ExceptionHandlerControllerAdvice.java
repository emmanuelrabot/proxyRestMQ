package com.proxyrestmq.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.proxyrestmq.services.MessageSendService;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    Logger logger = Logger.getLogger(MessageSendService.class.getName());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String genericHandler(Exception ex) {
        return "An error occured";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String nullPointerExceptionHandler(NullPointerException ex) {
        logger.log(Level.WARNING, "Null pointer exception occured.");
        return "Null pointer";
    }
}
