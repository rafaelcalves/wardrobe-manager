package br.com.correa.wardrobemanager.infra.controller;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.domain.exceptions.ElementAttributeInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ElementCodeConflictException.class)
    public ProblemDetail handleElementCodeConflictException(ElementCodeConflictException e, WebRequest request) {
        return getProblemDetail(HttpStatus.CONFLICT, e.getMessage(), request);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ProblemDetail handleElementNotFoundException(ElementNotFoundException e, WebRequest request) {
        return getProblemDetail(HttpStatus.NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler({ElementAttributeInvalidException.class})
    public ProblemDetail handleElementNotFoundException(ElementAttributeInvalidException e, WebRequest request) {
        return getProblemDetail(HttpStatus.BAD_REQUEST, e.getMessage(), request);
    }

    private static ProblemDetail getProblemDetail(HttpStatus status, String e, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e);
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return problemDetail;
    }
}
