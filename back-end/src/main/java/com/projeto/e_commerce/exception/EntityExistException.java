package com.projeto.e_commerce.exception;

public class EntityExistException extends RuntimeException {
    public EntityExistException(String message) {
        super(message);
    }
}
