package com.example.demo.Excepciones;

public class OperacionNoPermitidaException extends RuntimeException {
    public OperacionNoPermitidaException(String message) {
        super(message);
    }
}
