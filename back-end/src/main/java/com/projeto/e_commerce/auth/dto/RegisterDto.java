package com.projeto.e_commerce.auth.dto;

import com.projeto.e_commerce.auth.enums.RoleEnum;

public record RegisterDto(
    String name,
    String email,
    String password,
    RoleEnum role
) {}
