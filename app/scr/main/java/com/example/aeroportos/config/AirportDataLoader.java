package com.example.aeroportos.config;

import com.example.aeroportos.domain.Aeroporto;
import com.example.aeroportos.domain.DomainUtils;
import com.example.aeroportos.repository.AeroportoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AirportDataLoader {

    private final AeroportoRepository repository;

    private static final String CSV_URL =
            "https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv";

    @Bean
    CommandLineRunner loadAirportData() {
        return args -> {
            if (repository.count() > 0) {
                return; 
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URL(CSV_URL).openStream(), StandardCharsets.UTF_8))) {

                String line;
                boolean first = true;
                while ((line = br.readLine()) != null) {
                    if (first) {
                        first = false; 
                        continue;
                    }
                    String[] cols = line.split(";", -1); 

                    if (cols.length < 8) continue;

                    Long id = Long.parseLong(cols[0]);
                    String nome = cols[1];
                    String cidade = cols[2];
                    String paisNome = cols[3];
                    String iata = cols[4];
                    Double latitude = Double.parseDouble(cols[5]);
                    Double longitude = Double.parseDouble(cols[6]);
                    Double altitudePes = Double.parseDouble(cols[7]);
                    Double altitudeMetros = DomainUtils.converterPesParaMetros(altitudePes);

                    String iso = DomainUtils.obterIsoPais(paisNome);

                    if (iata == null || iata.isBlank() || iata.length() != 3) {
                        continue; 
                    }

                    Aeroporto aeroporto = Aeroporto.builder()
                            .idAeroporto(id)
                            .nomeAeroporto(nome)
                            .codigoIata(iata.toUpperCase())
                            .cidade(cidade)
                            .codigoPaisIso(iso)
                            .latitude(latitude)
                            .longitude(longitude)
                            .altitude(altitudeMetros)
                            .build();

                    repository.save(aeroporto);
                }
                log.info("Carga inicial de aeroportos concluída. Total: {}", repository.count());
            } catch (Exception e) {
                log.error("Erro ao carregar airports.csv", e);
            }
        };
    }
}
