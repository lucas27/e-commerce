package com.projeto.e_commerce.email.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record Email(
    @NotBlank(message = "O email do destinatário não pode ser vázio")
    String to,
    
    @NotBlank(message = "O subject não pode ser vázio")
    String subject,

    @NotEmpty(message = "deve adicionar uma mensagem")
    String body
) {}
