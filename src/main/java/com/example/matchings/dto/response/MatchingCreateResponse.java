package com.example.matchings.dto.response;

import com.example.matchings.entity.Matching;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MatchingCreateResponse(
        @JsonProperty("matching_id")
        Long matchingId
) {

    public static MatchingCreateResponse from(Matching matching) {
        return new MatchingCreateResponse(matching.getId());
    }
}
