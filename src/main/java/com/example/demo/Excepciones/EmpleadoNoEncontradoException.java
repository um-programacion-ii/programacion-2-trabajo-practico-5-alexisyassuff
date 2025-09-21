package com.example.demo.Excepciones;

public class EmpleadoNoEncontradoException extends RuntimeException {
    public EmpleadoNoEncontradoException(String message) {
        super(message);
    }
}
