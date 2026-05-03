package com.ventas.apiventas.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

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
    public ResponseEntity<ErrorResponse> manejarNotFound(NoEncontradoException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja errores de solicitud inválida (Bad Request).
     * Se usa para reglas de negocio simples o datos incorrectos enviados por el cliente.
     * Devuelve HTTP 400 BAD_REQUEST con el mensaje del error.
     */
    @ExceptionHandler(SolicitudIncorrectaException.class)
    public ResponseEntity<ErrorResponse> manejarBadRequest(SolicitudIncorrectaException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja conflictos en el sistema.
     * Se usa cuando la operación es válida pero no se puede ejecutar por el estado actual
     * (ej: stock insuficiente, duplicados).
     * Devuelve HTTP 409 CONFLICT.
     */
    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorResponse> manejarConflict(ConflictoException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja errores de validación de DTOs (@Valid en @RequestBody).
     * Se ejecuta cuando fallan las anotaciones como @NotNull, @NotEmpty, etc.
     * Devuelve HTTP 400 BAD_REQUEST con un mapa de campo → mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());});

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación en los campos enviados",
                request.getRequestURI(),
                errores
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores de validación en parámetros de URL (@PathVariable, @RequestParam).
     * Se ejecuta cuando fallan anotaciones como @Positive, @Min, etc.
     * Devuelve HTTP 400 BAD_REQUEST con un mensaje descriptivo del parámetro inválido.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarConstraint(ConstraintViolationException ex, HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        ex.getConstraintViolations().forEach(error -> {
            errores.put(error.getPropertyPath().toString(), error.getMessage());});

        String mensaje = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Parámetro inválido");

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                request.getRequestURI(),
                errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores de conversión de tipos en parámetros de la URL (@PathVariable, @RequestParam).
     * Se ejecuta cuando Spring no puede convertir un valor al tipo esperado
     * (por ejemplo: enviar "abc" cuando se espera un Long).
     * Devuelve HTTP 400 BAD_REQUEST con un mensaje indicando el parámetro inválido.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String mensaje = "El parámetro '" + ex.getName() + "' debe ser un número válido";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores de lectura del cuerpo de la solicitud.
     * Se ejecuta cuando el JSON enviado está mal formado o contiene datos inválidos
     * para ser convertidos al DTO.
     * Devuelve HTTP 400 BAD_REQUEST con un mensaje genérico.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {

        String mensaje = "JSON inválido o mal formado";

        if (ex.getMessage().contains("Required request body is missing"))
            mensaje = "El cuerpo de la solicitud es obligatorio";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores cuando se usa un método HTTP no permitido.
     * Se ejecuta cuando el endpoint existe pero se usa un método incorrecto
     * (ej: hacer POST en una ruta que solo acepta GET).
     * Devuelve HTTP 405 METHOD_NOT_ALLOWED.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMetodoNoPermitido(HttpRequestMethodNotSupportedException ex,
                                                                  HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.name(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    /**
     * Maneja errores de integridad en base de datos.
     * Se ejecuta cuando se viola una restricción, como datos únicos duplicados
     * (ej: correo ya registrado).
     * Devuelve HTTP 409 CONFLICT con un mensaje descriptivo.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicados(DataIntegrityViolationException ex, HttpServletRequest request){

        String mensaje = "Error de integridad de datos";

        if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("correo")) {
            mensaje = "El correo ya está registrado";
        }

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value(),
                mensaje,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja rutas inexistentes.
     * Se ejecuta cuando el cliente intenta acceder a un endpoint que no existe.
     * Devuelve HTTP 404 NOT_FOUND con información de la ruta solicitada.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoEncontrada(NoHandlerFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja errores cuando falta un parámetro obligatorio en la solicitud (@RequestParam).
     * Se ejecuta cuando el cliente NO envía un parámetro requerido en la URL.
     * Devuelve HTTP 400 BAD_REQUEST con el nombre del parámetro faltante.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> manejarParametroFaltante(MissingServletRequestParameterException ex, HttpServletRequest request) {

        String mensaje = "Falta el parámetro obligatorio: " + ex.getParameterName();

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores cuando el tipo de contenido enviado no es soportado.
     * Se ejecuta cuando el cliente envía un Content-Type incorrecto
     * (por ejemplo: text/plain en lugar de application/json).
     * Devuelve HTTP 415 UNSUPPORTED_MEDIA_TYPE.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.name(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "Tipo de contenido no soportado",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    /**
     * Maneja errores inesperados del sistema.
     * Captura cualquier excepción no controlada previamente.
     * Devuelve HTTP 500 INTERNAL_SERVER_ERROR con un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}