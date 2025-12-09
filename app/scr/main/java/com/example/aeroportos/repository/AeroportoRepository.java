package com.example.aeroportos.repository;

import com.example.aeroportos.domain.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Long> {

    Optional<Aeroporto> findByCodigoIata(String codigoIata);

    void deleteByCodigoIata(String codigoIata);

    boolean existsByCodigoIata(String codigoIata);
}
