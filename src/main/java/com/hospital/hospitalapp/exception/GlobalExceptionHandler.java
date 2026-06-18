package com.hospital.hospitalapp.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.CONFLICT);
        mav.addObject("status", HttpStatus.CONFLICT.value());
        mav.addObject("message", "No se puede completar la operacion porque hay datos relacionados que la impiden.");
        return mav;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFound(EntityNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("message", ex.getMessage() != null ? ex.getMessage() : "Recurso no encontrado.");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject("message", "Ha ocurrido un error inesperado.");
        return mav;
    }
}
