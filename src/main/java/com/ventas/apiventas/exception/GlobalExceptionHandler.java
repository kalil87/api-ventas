package com.ventas.apiventas.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

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
     * Maneja errores cuando se usa un método HTTP no permitido.
     * Se ejecuta cuando el endpoint existe pero se usa un método incorrecto
     * (ej: hacer POST en una ruta que solo acepta GET).
     * Devuelve HTTP 405 METHOD_NOT_ALLOWED.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMetodoNoPermitido(
            HttpRequestMethodNotSupportedException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "METHOD_NOT_ALLOWED",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    /**
     * Maneja errores de integridad en base de datos.
     * Se ejecuta cuando se viola una restricción, como datos únicos duplicados
     * (ej: correo ya registrado).
     * Devuelve HTTP 409 CONFLICT con un mensaje descriptivo.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicados(DataIntegrityViolationException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                "El correo ya está registrado",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja rutas inexistentes.
     * Se ejecuta cuando el cliente intenta acceder a un endpoint que no existe.
     * Devuelve HTTP 404 NOT_FOUND con información de la ruta solicitada.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoEncontrada(NoHandlerFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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

    /**
     * Maneja errores de lectura del cuerpo de la solicitud.
     * Se ejecuta cuando el JSON enviado está mal formado o contiene datos inválidos
     * para ser convertidos al DTO.
     * Devuelve HTTP 400 BAD_REQUEST con un mensaje genérico.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(
            HttpMessageNotReadableException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                "JSON inválido o mal formado",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}