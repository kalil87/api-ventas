package com.ventas.apiventas.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo NoEncontradoException.
     * Se usa cuando un recurso no existe (ej: cliente, producto, venta).
     * Devuelve HTTP 404 NOT_FOUND con el mensaje del error.
     */
    @ExceptionHandler(NoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNotFound(NoEncontradoException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "NOT_FOUND",
                        ex.getMessage(),
                        LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Maneja errores de solicitud inválida (Bad Request).
     * Se usa para reglas de negocio simples o datos incorrectos enviados por el cliente.
     * Devuelve HTTP 400 BAD_REQUEST con el mensaje del error.
     */
    @ExceptionHandler(SolicitudIncorrectaException.class)
    public ResponseEntity<ErrorResponse> manejarBadRequest(SolicitudIncorrectaException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "BAD_REQUEST",
                        ex.getMessage(),
                        LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Maneja conflictos en el sistema.
     * Se usa cuando la operación es válida pero no se puede ejecutar por el estado actual
     * (ej: stock insuficiente, duplicados).
     * Devuelve HTTP 409 CONFLICT.
     */
    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorResponse> manejarConflict(ConflictoException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "CONFLICT",
                        ex.getMessage(),
                        LocalDateTime.now()),
                HttpStatus.CONFLICT
        );
    }

    /**
     * Maneja errores de validación de DTOs (@Valid en @RequestBody).
     * Se ejecuta cuando fallan las anotaciones como @NotNull, @NotEmpty, etc.
     * Devuelve HTTP 400 BAD_REQUEST con un mapa de campo → mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Maneja errores de validación en parámetros de URL (@PathVariable, @RequestParam).
     * Se ejecuta cuando fallan anotaciones como @Positive, @Min, etc.
     * Devuelve HTTP 400 BAD_REQUEST con un mensaje descriptivo del parámetro inválido.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarConstraint(ConstraintViolationException ex) {

        String mensaje = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Parámetro inválido");

        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "BAD_REQUEST",
                        mensaje,
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Maneja errores inesperados del sistema.
     * Captura cualquier excepción no controlada previamente.
     * Devuelve HTTP 500 INTERNAL_SERVER_ERROR con un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_SERVER_ERROR",
                        "Ocurrió un error inesperado",
                        LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}