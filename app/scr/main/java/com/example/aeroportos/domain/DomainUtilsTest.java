package com.example.aeroportos.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainUtilsTest {

    @Test
    void converterPesParaMetros_deveConverterCorretamente() {
        double resultado = DomainUtils.converterPesParaMetros(1000);
        assertEquals(304.8, resultado, 0.0001);
    }

    @Test
    void obterIsoPais_deveRetornarBRParaBrazil() {
        String iso = DomainUtils.obterIsoPais("Brazil");
        assertEquals("BR", iso);
    }

    @Test
    void obterIsoPais_deveRetornarBRParaBrasil() {
        String iso = DomainUtils.obterIsoPais("Brasil");
        assertEquals("BR", iso);
    }
}
