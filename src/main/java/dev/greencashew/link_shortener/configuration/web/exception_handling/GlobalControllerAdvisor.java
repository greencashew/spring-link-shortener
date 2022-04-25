package dev.greencashew.link_shortener.configuration.web.exception_handling;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
class GlobalControllerAdvisor {

    @ApiResponse(description = "Invalid data provided.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"expirationDate (2014-06-23) must be a future date, email (incorrect_email) must be a well-formed email address\"}")))
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        final BindingResult result = ex.getBindingResult();

        final String fieldErrors = result.getAllErrors()
                .stream()
                .map(error -> error instanceof FieldError fieldError ?
                        fieldError.getField() + " (" + fieldError.getRejectedValue() + ") " + fieldError.getDefaultMessage() :
                        error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ExceptionResponse(fieldErrors);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleError(Exception ex, WebRequest request) {
        log.error("Unexpected exception for web request: " + request, ex);
        return new ExceptionResponse(ex.getMessage());
    }
}
