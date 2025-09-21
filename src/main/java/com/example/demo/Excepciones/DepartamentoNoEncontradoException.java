package com.example.demo.Excepciones;

public class DepartamentoNoEncontradoException extends RuntimeException {
    public DepartamentoNoEncontradoException(String message) {
        super(message);
    }
}
