package com.projeto.e_commerce.exception.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionMessageDto{
    private Integer exceptionCode;
    private String message;
    private LocalDateTime exceptionDateTime;
} 
