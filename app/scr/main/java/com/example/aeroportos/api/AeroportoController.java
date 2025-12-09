package com.example.aeroportos.api;

import com.example.aeroportos.domain.Aeroporto;
import com.example.aeroportos.service.AeroportoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/aeroportos")
@RequiredArgsConstructor
public class AeroportoController {

    private final AeroportoService service;

    @GetMapping
    public List<Aeroporto> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{iata}")
    public ResponseEntity<Aeroporto> buscarPorIata(@PathVariable String iata) {
        Aeroporto aeroporto = service.buscarPorIata(iata);
        return ResponseEntity.ok(aeroporto);
    }

    @PostMapping
    public ResponseEntity<Aeroporto> criar(@Valid @RequestBody Aeroporto aeroporto) {
        Aeroporto criado = service.criar(aeroporto);
        URI location = URI.create("/api/v1/aeroportos/" + criado.getCodigoIata());
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{iata}")
    public ResponseEntity<Aeroporto> atualizar(@PathVariable String iata,
                                               @Valid @RequestBody Aeroporto aeroporto) {
        Aeroporto atualizado = service.atualizar(iata, aeroporto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{iata}")
    public ResponseEntity<Void> excluir(@PathVariable String iata) {
        service.excluir(iata);
        return ResponseEntity.noContent().build();
    }
}
