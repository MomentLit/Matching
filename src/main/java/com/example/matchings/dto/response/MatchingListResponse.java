package com.example.matchings.dto.response;

import java.util.List;

public record MatchingListResponse(
        List<MatchingSearchResponse> matchings
) {
}
