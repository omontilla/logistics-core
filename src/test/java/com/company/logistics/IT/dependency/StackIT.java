package com.company.logistics.IT.dependency;

import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.infrastructure.db.repository.ShipmentStatusRepository;
import com.company.logistics.util.MockShipmentData;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Json;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class StackIT {

    @Autowired
    public ShipmentStatusRepository statusRepository;

    @Autowired
    public ShipmentRepository shipmentRepository;

    protected static final WireMockServer wireMockServer = new WireMockServer(8085);

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("logisticsdb")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
            WireMock.configureFor("localhost", 8085);
        }
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public WireMockServer wireMockServer() {
            return wireMockServer;
        }
    }

    @BeforeEach
    void setupStub() {
        WireMock.reset();

        stubFor(get(urlPathEqualTo("/mock/distance"))
                .withQueryParam("origin", equalTo("Madrid"))
                .withQueryParam("destination", equalTo("Barcelona"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(Json.write(MockShipmentData.mockDistanceResponse))));
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void deleteData(){
        shipmentRepository.deleteAll();
        statusRepository.deleteAll();
    }
}
