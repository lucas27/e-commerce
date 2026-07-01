package com.projeto.e_commerce.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDto(
    @Schema(defaultValue = "email", description = "adicione o email cadastrado no DB", example = "teste@gmail.com")
    String login,
    String password
) {}
