package com.example.matchings.dto.response;

import com.example.matchings.entity.Matching;
import com.example.matchings.entity.MatchingStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record MatchingSearchResponse(
        @JsonProperty("matching_id")
        Long matchingId,

        @JsonProperty("space_id")
        Long spaceId,

        @JsonProperty("start_time")
        LocalDateTime startTime,

        @JsonProperty("end_time")
        LocalDateTime endTime,

        @JsonProperty("total_price")
        Integer totalPrice,

        MatchingStatus status,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {

    public static MatchingSearchResponse from(Matching matching) {
        return new MatchingSearchResponse(
                matching.getId(),
                matching.getSpaceId(),
                matching.getStartTime(),
                matching.getEndTime(),
                matching.getTotalPrice(),
                matching.getStatus(),
                matching.getCreatedAt()
        );
    }
}
