package com.example.aeroportos.service.exception;

public class AeroportoNaoEncontradoException extends RuntimeException {

    public AeroportoNaoEncontradoException(String codigoIata) {
        super("Aeroporto com código IATA '%s' não encontrado.".formatted(codigoIata));
    }
}
