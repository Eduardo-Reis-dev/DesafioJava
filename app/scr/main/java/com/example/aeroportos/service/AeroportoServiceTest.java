package com.example.aeroportos.service;

import com.example.aeroportos.domain.Aeroporto;
import com.example.aeroportos.repository.AeroportoRepository;
import com.example.aeroportos.service.exception.AeroportoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AeroportoServiceTest {

    @Mock
    private AeroportoRepository repository;

    @InjectMocks
    private AeroportoService service;

    @Test
    void buscarPorIata_deveLancarExcecaoQuandoNaoEncontrar() {
        when(repository.findByCodigoIata("AAA"))
                .thenReturn(Optional.empty());

        assertThrows(AeroportoNaoEncontradoException.class,
                () -> service.buscarPorIata("AAA"));
    }

    @Test
    void criar_deveLancarExcecaoQuandoCodigoIataInvalido() {
        Aeroporto aeroporto = Aeroporto.builder()
                .nomeAeroporto("Teste Airport")
                .codigoIata("AAAA") // 4 letras
                .cidade("Cidade")
                .codigoPaisIso("BR")
                .latitude(1.0)
                .longitude(2.0)
                .altitude(100.0)
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> service.criar(aeroporto));

        verify(repository, never()).save(ArgumentMatchers.any());
    }

    @Test
    void criar_deveLancarExcecaoQuandoAltitudeNegativa() {
        Aeroporto aeroporto = Aeroporto.builder()
                .nomeAeroporto("Teste Airport")
                .codigoIata("AAA")
                .cidade("Cidade")
                .codigoPaisIso("BR")
                .latitude(1.0)
                .longitude(2.0)
                .altitude(-10.0)
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> service.criar(aeroporto));

        verify(repository, never()).save(ArgumentMatchers.any());
    }
}
