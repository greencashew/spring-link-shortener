package dev.greencashew.link_shortener.controller;

import dev.greencashew.link_shortener.configuration.web.exception_handling.ExceptionResponse;
import dev.greencashew.link_shortener.link.api.exception.IncorrectAdminVerificationException;
import dev.greencashew.link_shortener.link.api.exception.LinkAlreadyExistsException;
import dev.greencashew.link_shortener.link.api.exception.LinkNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Hidden
@ControllerAdvice
class LinkManageControllerAdvisor {

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ExceptionResponse handleLinkAlreadyExistsException(LinkAlreadyExistsException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LinkNotFoundException.class)
    public ExceptionResponse handleLinkNotFoundException(LinkNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IncorrectAdminVerificationException.class)
    public ExceptionResponse handleIncorrectAdminVerificationException(IncorrectAdminVerificationException ex, WebRequest request) {
        log.info("Incorrect admin verification for " + request, ex);
        return new ExceptionResponse(ex.getMessage());
    }
}
