package com.example.matching.client;

import com.example.matching.client.dto.SpaceMatchingContextResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
public class SpaceClient {

    private final RestClient restClient;

    public SpaceClient(
            @Value("${space-service.base-url}") String baseUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public SpaceMatchingContextResponse getMatchingContext(
            Long spaceId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        SpaceMatchingContextResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/internal/spaces/{spaceId}/matching-context")
                        .queryParam("start-time", startTime)
                        .queryParam("end-time", endTime)
                        .build(spaceId))
                .retrieve()
                .body(SpaceMatchingContextResponse.class);

        if (response == null) {
            throw new IllegalStateException("공간 정보를 조회할 수 없습니다.");
        }

        return response;
    }
}
