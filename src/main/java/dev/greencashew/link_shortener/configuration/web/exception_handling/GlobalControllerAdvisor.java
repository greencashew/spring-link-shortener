package dev.greencashew.link_shortener.configuration.web.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
class GlobalControllerAdvisor {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleError(Exception ex, WebRequest request) {
        log.error("Unexpected exception for web request: " + request, ex);
        return new ExceptionResponse(ex.getMessage());
    }
}
