package com.sriram.immigrationservice.api;

import com.sriram.immigrationservice.ImmigrationServiceApplication;
import com.sriram.immigrationservice.model.Immigration;
import com.sriram.immigrationservice.repository.ImmigrationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {ImmigrationServiceApplication.class})
@WebFluxTest(ImmigrationAPI.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ImmigrationApiIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ImmigrationRepository immigrationRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testAllImmigration() throws Exception {
        Immigration immigration = new Immigration("123", "TST1234567890", "Approved", "test description");
        Flux<Immigration> flux = Flux.just(immigration);

        Mockito.when(immigrationRepository.findAll())
                .thenReturn(flux);

        webTestClient.get()
                .uri("/immigration")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Immigration.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assert.isTrue(response.get(0).getReceiptNumber().equals(immigration.getReceiptNumber()), "TST1234567890");
                });

    }

    @Test
    public void getsSingleImmigration() throws Exception {
        Immigration immigration = new Immigration("123", "TST1234567890", "Approved", "test description");
        Mono<Immigration> mono = Mono.just(immigration);

        Mockito.when(immigrationRepository.findById("123"))
                .thenReturn(mono);

        webTestClient.get()
                .uri("/immigration/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Immigration.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assert.isTrue(response.get(0).getId().equals(immigration.getId()), "is equal");
                });
    }

    @Test
    public void returnsNotFoundForInvalidImmigration() throws Exception {
        Flux<Immigration> flux = Flux.empty();

        Mockito.when(immigrationRepository.findAll())
                .thenReturn(flux);

        webTestClient.get()
                .uri("/immigration/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class)
                ;

    }

    @Test
    public void addsNewImmigration() throws Exception {
        Immigration immigration = new Immigration("123", "TST1234567890", "Approved", "test description");
        Mono<Immigration> mono = Mono.just(immigration);

        Mockito.when(immigrationRepository.save(immigration))
                .thenReturn(mono);

        webTestClient.post()
                .uri("/immigration")
                .bodyValue(immigration)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Immigration.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assert.isTrue(response.getId().equals(immigration.getId()), "is equal");
                });


    }

}
