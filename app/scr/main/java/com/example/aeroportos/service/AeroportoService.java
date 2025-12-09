package com.example.aeroportos.service;

import com.example.aeroportos.domain.Aeroporto;
import com.example.aeroportos.repository.AeroportoRepository;
import com.example.aeroportos.service.exception.AeroportoNaoEncontradoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeroportoService {

    private final AeroportoRepository repository;

    @Transactional(readOnly = true)
    public List<Aeroporto> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Aeroporto buscarPorIata(String iata) {
        String codigo = normalizarIata(iata);
        return repository.findByCodigoIata(codigo)
                .orElseThrow(() -> new AeroportoNaoEncontradoException(codigo));
    }

    @Transactional
    public Aeroporto criar(@Valid Aeroporto aeroporto) {
        validarNegocio(aeroporto);

        aeroporto.setIdAeroporto(null);
        aeroporto.setCodigoIata(normalizarIata(aeroporto.getCodigoIata()));

        return repository.save(aeroporto);
    }

    @Transactional
    public Aeroporto atualizar(String iata, @Valid Aeroporto dadosAtualizados) {
        String codigo = normalizarIata(iata);

        Aeroporto existente = repository.findByCodigoIata(codigo)
                .orElseThrow(() -> new AeroportoNaoEncontradoException(codigo));

        validarNegocio(dadosAtualizados);

        existente.setNomeAeroporto(dadosAtualizados.getNomeAeroporto());
        existente.setCidade(dadosAtualizados.getCidade());
        existente.setCodigoPaisIso(dadosAtualizados.getCodigoPaisIso());
        existente.setLatitude(dadosAtualizados.getLatitude());
        existente.setLongitude(dadosAtualizados.getLongitude());
        existente.setAltitude(dadosAtualizados.getAltitude());

        // codigo IATA do path é a referência principal
        existente.setCodigoIata(codigo);

        return repository.save(existente);
    }

    @Transactional
    public void excluir(String iata) {
        String codigo = normalizarIata(iata);

        if (!repository.existsByCodigoIata(codigo)) {
            throw new AeroportoNaoEncontradoException(codigo);
        }
        repository.deleteByCodigoIata(codigo);
    }

    private void validarNegocio(Aeroporto aeroporto) {
        if (aeroporto.getCodigoIata() == null || aeroporto.getCodigoIata().length() != 3) {
            throw new IllegalArgumentException("codigo_iata deve ter exatamente 3 letras.");
        }
        if (aeroporto.getAltitude() != null && aeroporto.getAltitude() < 0) {
            throw new IllegalArgumentException("altitude não pode ser negativa.");
        }
    }

    private String normalizarIata(String iata) {
        if (iata == null) {
            throw new IllegalArgumentException("codigo_iata não pode ser nulo.");
        }
        return iata.trim().toUpperCase();
    }
}
