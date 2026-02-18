package com.bluemar.chess.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<ErrorMessage> handleInvalidMove(InvalidMoveException ex) {
        ErrorMessage error = new ErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    public record ErrorMessage(String error) {}
}
