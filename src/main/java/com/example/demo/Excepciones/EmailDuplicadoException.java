package com.example.demo.Excepciones;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String message) {
        super(message);
    }
}