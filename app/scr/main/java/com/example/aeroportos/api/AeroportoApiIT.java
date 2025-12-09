package com.example.aeroportos.api;

import com.example.aeroportos.domain.Aeroporto;
import com.example.aeroportos.repository.AeroportoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AeroportoApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AeroportoRepository repository;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/aeroportos";
    }

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void fluxoCompletoCrudDeveFuncionar() {

        Aeroporto novo = Aeroporto.builder()
                .nomeAeroporto("Test Airport")
                .codigoIata("TST")
                .cidade("Test City")
                .codigoPaisIso("BR")
                .latitude(-19.9)
                .longitude(-43.9)
                .altitude(900.0)
                .build();

        ResponseEntity<Aeroporto> postResponse =
                restTemplate.postForEntity(baseUrl(), novo, Aeroporto.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertEquals("TST", postResponse.getBody().getCodigoIata());
        ResponseEntity<Aeroporto> getResponse =
                restTemplate.getForEntity(baseUrl() + "/TST", Aeroporto.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Test Airport", getResponse.getBody().getNomeAeroporto());

        Aeroporto atualizado = getResponse.getBody();
        atualizado.setNomeAeroporto("Updated Airport");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Aeroporto> putEntity = new HttpEntity<>(atualizado, headers);
        ResponseEntity<Aeroporto> putResponse =
                restTemplate.exchange(baseUrl() + "/TST", HttpMethod.PUT, putEntity, Aeroporto.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(putResponse.getBody());
        assertEquals("Updated Airport", putResponse.getBody().getNomeAeroporto());

        ResponseEntity<Void> deleteResponse =
                restTemplate.exchange(baseUrl() + "/TST", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<String> getAfterDelete =
                restTemplate.getForEntity(baseUrl() + "/TST", String.class);

        assertEquals(HttpStatus.NOT_FOUND, getAfterDelete.getStatusCode());
    }
}
