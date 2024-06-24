package sctp.ntu.booking_api.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sctp.ntu.booking_api.entities.ErrorResponse;
import sctp.ntu.booking_api.exceptions.UserNotFoundException;
import sctp.ntu.booking_api.exceptions.BookingNotFoundException;
import sctp.ntu.booking_api.exceptions.EventNotFoundException;
import sctp.ntu.booking_api.exceptions.SeatsNotEnoughException;

@ControllerAdvice
public class GlobalExceptionHandler {

  // this is handler for CustomerNotFoundException
  @ExceptionHandler({ UserNotFoundException.class, BookingNotFoundException.class, EventNotFoundException.class })
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(RuntimeException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SeatsNotEnoughException.class)
  public ResponseEntity<ErrorResponse> handleSeatsNotEnoughException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // [Activity 1 - Refactor]
  // @ExceptionHandler(InteractionNotFoundException.class)
  // public ResponseEntity<ErrorResponse>
  // handleInteractionNotFoundException(CustomerNotFoundException ex){
  // ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
  // LocalDateTime.now());
  // return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  // }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Item does not exist.", LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    // Get a list of all validation errors from the exception object
    List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();

    // Create a StringBuilder to store all error messages
    StringBuilder sb = new StringBuilder();

    for (ObjectError error : validationErrors) {
      sb.append(error.getDefaultMessage() + ".");
    }

    ErrorResponse errorResponse = new ErrorResponse(sb.toString(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    // We can log the exception here
    // logger.error(ex.getMessage(), ex);

    // return generic error message;
    ErrorResponse errorResponse = new ErrorResponse("Something went wrong", LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
