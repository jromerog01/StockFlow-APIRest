package com.jesus.stockflow.exceptions;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

    @ExceptionHandler(IdInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleIdInvalido(IdInvalidoException ex){
        return crearBody(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({CamposInvalidosException.class, NombreInvalidoException.class})
    public ResponseEntity<ErrorResponseDTO> handleCampoInvalido(RuntimeException ex){
       return crearBody(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleIntegridadReferencial(DataIntegrityViolationException ex){
        return crearBody(HttpStatus.CONFLICT,
                "No se puede completar la operacion porque el recurso esta en conflicto con otro registro");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleBodyIlegible(HttpMessageNotReadableException ex){
        return crearBody(HttpStatus.BAD_REQUEST,
                "El cuerpo de la peticion es invalido o tiene valores con formato incorrecto");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTipoParametroInvalido(MethodArgumentTypeMismatchException ex){
        return crearBody(HttpStatus.BAD_REQUEST,
                "El parametro '" + ex.getName() + "' tiene un valor invalido");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex){
        logger.error("Error inesperado", ex);
        return crearBody(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado");
    }

    private ResponseEntity<ErrorResponseDTO> crearBody(HttpStatus httpStatus, String message){
        ErrorResponseDTO body = new ErrorResponseDTO(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(httpStatus).body(body);
    }





}