package com.example.tourappapi.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Something bad happened.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Please fill out all of the fields.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {MethodNotAllowedException.class})
    public ResponseEntity<Object> handleMethodNotAllowedException(MethodNotAllowedException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), "This method not allowed for this request.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());

    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(MethodNotAllowedException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), "No value present.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());

    }

    @ExceptionHandler(value = {LoginException.class})
    public ResponseEntity<Object> handleLoginException(LoginException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Email or password is incorrect.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {OldPassIsIncorrectException.class})
    public ResponseEntity<Object> handleOldPassIsIncorrectException(OldPassIsIncorrectException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Old password is incorrect.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {PasswordsAreTheSameException.class})
    public ResponseEntity<Object> handlePasswordsAreTheSameException(PasswordsAreTheSameException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "your new password cannot be the same as your old password.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {NoSuchEmailException.class})
    public ResponseEntity<Object> handleEmailNotFoundException(NoSuchEmailException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), "No user found with this email");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> handleTokenInvalidException(InvalidTokenException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Token is invalid");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,
                ex.getLocalizedMessage(), "This email is already used.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,
                ex.getLocalizedMessage(), "This username is already used.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {VoenAlreadyExistsException.class})
    public ResponseEntity<Object> handleVoenAlreadyExistsException(VoenAlreadyExistsException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,
                ex.getLocalizedMessage(), "This voen is already used.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {RequestInactiveException.class})
    public ResponseEntity<Object> handleRequestInactiveException(RequestInactiveException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Request is inactive or expired");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {RequestIsArchivedException.class})
    public ResponseEntity<Object> handleRequestIsArchivedException(RequestIsArchivedException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Request is archived");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {NotWorkTimeException.class})
    public ResponseEntity<Object> handleNotWorkTimeException(NotWorkTimeException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "It is not working hours");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {TokenExpiredException.class})
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Token is expired");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), "User not found");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {UserRequestNotFoundException.class})
    public ResponseEntity<Object> handleUserRequestNotFoundException(UserRequestNotFoundException ex,
                                                                     WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(), "Can't find user request with this id.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }


    @ExceptionHandler(value = {AlreadyHaveOfferException.class})
    public ResponseEntity<Object> handleAlreadyHaveOfferException(AlreadyHaveOfferException ex,
                                                                     WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,
                ex.getLocalizedMessage(), "You already have an offer for this request.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {NoSuchStatusException.class})
    public ResponseEntity<Object> handleNoSuchStatusException(NoSuchStatusException ex,
                                                                  WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Can't find such status.");
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
