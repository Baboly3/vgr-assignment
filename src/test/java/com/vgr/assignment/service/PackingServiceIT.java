package com.vgr.assignment.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PackingServiceIT {

    @Autowired
    private PackingService packingService;

    static Stream<ServiceTestScenario> serviceScenarios() {
        return Stream.of(
                new ServiceTestScenario(
                        Map.of(7,6, 4,2, 1,4),
                        Optional.of(2)
                ),
                new ServiceTestScenario(
                        Map.of(3,3, 1,1, 2,1),
                        Optional.of(1)
                ),
                new ServiceTestScenario(
                        Map.of(5,1, 4,3),
                        Optional.of(2)
                ),
                new ServiceTestScenario(
                        Map.of(7,12, 1,100),
                        Optional.empty()
                ),
                new ServiceTestScenario(
                        Map.of(8,4),
                        Optional.of(1)
                ),
                new ServiceTestScenario(
                        Map.of(7,4, 6,4, 3,3),
                        Optional.of(3)
                ),
                new ServiceTestScenario(
                        Map.of(6,1, 3,2, 7,2),
                        Optional.of(2)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("serviceScenarios")
    void serviceShouldReturnExpectedBox(ServiceTestScenario scenario) {
        Optional<Integer> actualBox = packingService.chooseBox(scenario.getOrder());
        assertEquals(scenario.getExpectedBox(), actualBox,
                "För order " + scenario.getOrder());
    }

    private static class ServiceTestScenario {
        private final Map<Integer,Integer> order;
        private final Optional<Integer> expectedBox;

        ServiceTestScenario(Map<Integer,Integer> order,
                            Optional<Integer> expectedBox) {
            this.order       = order;
            this.expectedBox = expectedBox;
        }
        Map<Integer,Integer> getOrder()      { return order; }
        Optional<Integer> getExpectedBox()   { return expectedBox; }
    }
}