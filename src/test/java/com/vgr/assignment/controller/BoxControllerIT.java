package com.vgr.assignment.controller;

import com.vgr.assignment.dto.ArticleCount;
import com.vgr.assignment.dto.BoxRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoxControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    private String url() {
        return "http://localhost:" + port + "/api/box";
    }

    static Stream<TestScenario> requestScenarios() {
        return Stream.of(
                new TestScenario(
                        List.of(
                                new ArticleCount(7,6),
                                new ArticleCount(4,2),
                                new ArticleCount(1,4)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 2),
                        null
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(3,3),
                                new ArticleCount(1,1),
                                new ArticleCount(2,1)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 1),
                        null
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(5,1),
                                new ArticleCount(4,3)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 2),
                        null
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(7,12),
                                new ArticleCount(1,100)
                        ),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        null,
                        "Upphämtning krävs"
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(8,4)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 1),
                        null
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(7,4),
                                new ArticleCount(6,4),
                                new ArticleCount(3,3)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 3),
                        null
                ),
                new TestScenario(
                        List.of(
                                new ArticleCount(6,1),
                                new ArticleCount(3,2),
                                new ArticleCount(7,2)
                        ),
                        HttpStatus.OK,
                        Map.of("box", 2),
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("requestScenarios")
    void controllerShouldReturnExpectedResponse(TestScenario scenario) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BoxRequest> requestEntity = new HttpEntity<>(
                new BoxRequest(scenario.getRequestItems()), headers
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(url(), requestEntity, Map.class);

        assertEquals(scenario.getExpectedStatus(), response.getStatusCode(),
                "För " + scenario.getRequestItems());

        if (scenario.getExpectedBody() != null) {
            scenario.getExpectedBody().forEach((key, value) ->
                    assertEquals(value, response.getBody().get(key),
                            "Body[" + key + "]")
            );
        }
        if (scenario.getExpectedMessage() != null) {
            assertEquals(scenario.getExpectedMessage(), response.getBody().get("message"),
                    "message");
        }
    }

    private static class TestScenario {
        private final List<ArticleCount> requestItems;
        private final HttpStatus expectedStatus;
        private final Map<String, Object> expectedBody;
        private final String expectedMessage;

        TestScenario(List<ArticleCount> requestItems,
                     HttpStatus expectedStatus,
                     Map<String, Object> expectedBody,
                     String expectedMessage) {
            this.requestItems   = requestItems;
            this.expectedStatus = expectedStatus;
            this.expectedBody   = expectedBody;
            this.expectedMessage= expectedMessage;
        }
        List<ArticleCount> getRequestItems()   { return requestItems; }
        HttpStatus getExpectedStatus()         { return expectedStatus; }
        Map<String,Object> getExpectedBody()   { return expectedBody; }
        String getExpectedMessage()            { return expectedMessage; }
    }
}

