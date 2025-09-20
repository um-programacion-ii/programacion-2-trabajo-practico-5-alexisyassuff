package org.example.Excepciones;

public class DepartamentoNoEncontradoException extends RuntimeException {
    public DepartamentoNoEncontradoException(String message) {
        super(message);
    }
}
