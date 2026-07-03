package com.projeto.e_commerce.exception;

public class QuantityExceededException extends RuntimeException{
    public QuantityExceededException(String message) {
        super(message);
    }
}
