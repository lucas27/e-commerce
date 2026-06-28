package com.projeto.e_commerce.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.projeto.e_commerce.exception.EntityExistException;
import com.projeto.e_commerce.exception.IdNotFoundException;
import com.projeto.e_commerce.exception.dto.ExceptionMessageDto;

@RestControllerAdvice
public class ExceptionsHadler {
    
    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<ExceptionMessageDto> Exception(EntityExistException e) {
        ExceptionMessageDto dto = new ExceptionMessageDto();

        dto.setExceptionCode(HttpStatus.CONFLICT.value());
        dto.setMessage(e.getMessage());
        dto.setExceptionDateTime(LocalDateTime.now());
        return ResponseEntity.status(dto.getExceptionCode()).body(dto);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> Exception(IdNotFoundException e) {
        ExceptionMessageDto dto = new ExceptionMessageDto();

        dto.setExceptionCode(HttpStatus.NOT_FOUND.value());
        dto.setMessage(e.getMessage());
        dto.setExceptionDateTime(LocalDateTime.now());
        return ResponseEntity.status(dto.getExceptionCode()).body(dto);
    }
}
