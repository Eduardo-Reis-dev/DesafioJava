package com.example.aeroportos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "aeroporto",
       indexes = {
           @Index(name = "idx_aeroporto_iata", columnList = "codigo_iata", unique = true)
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aeroporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aeroporto")
    private Long idAeroporto;

    @NotBlank
    @Column(name = "nome_aeroporto", nullable = false)
    private String nomeAeroporto;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "codigo_iata", length = 3, nullable = false, unique = true)
    private String codigoIata;

    @NotBlank
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(name = "codigo_pais_iso", length = 2, nullable = false)
    private String codigoPaisIso;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "altitude", nullable = false)
    private Double altitude; // metros
}
