package com.example.matching.service;

import com.example.matching.dto.request.MatchingCreateRequest;
import com.example.matching.dto.response.MatchingCreateResponse;
import com.example.matching.dto.response.MatchingListResponse;
import com.example.matching.dto.response.MatchingSearchResponse;
import com.example.matching.entity.Matching;
import com.example.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;

    @Transactional
    public MatchingCreateResponse create(String userId, MatchingCreateRequest request) {
        LocalDateTime startTime = parseTime(request.startTime());
        LocalDateTime endTime = parseTime(request.endTime());

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("종료 시간은 시작 시간 이후여야 함");
        }

        Integer totalPrice = parsePrice(request.totalPrice());

        Matching matching = Matching.create(
                request.spaceId(),
                userId,
                startTime,
                endTime,
                totalPrice
        );

        matchingRepository.save(matching);

        return MatchingCreateResponse.from(matching);
    }

    @Transactional(readOnly = true)
    public MatchingListResponse getReceivedMatchings(String userId) {
        return toListResponse(List.of());
    }

    @Transactional(readOnly = true)
    public MatchingListResponse getSentMatchings(String userId) {
        return toListResponse(matchingRepository.findByRequesterIdOrderByCreatedAtDesc(userId));
    }

    @Transactional
    public void approve(String userId, Long matchingId) {
        Matching matching = getMatching(matchingId);
        matching.approve(userId);
    }

    @Transactional
    public void reject(String userId, Long matchingId) {
        Matching matching = getMatching(matchingId);
        matching.reject(userId);
    }

    private MatchingListResponse toListResponse(List<Matching> matchings) {
        List<MatchingSearchResponse> responses = matchings.stream()
                .map(MatchingSearchResponse::from)
                .toList();

        return new MatchingListResponse(responses);
    }

    private Matching getMatching(Long matchingId) {
        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("매칭 없음"));
    }

    private LocalDateTime parseTime(String value) {
        try {
            return OffsetDateTime.parse(value).toLocalDateTime();
        } catch (Exception e) {
            try {
                return LocalDateTime.parse(value);
            } catch (Exception ignored) {
                throw new IllegalArgumentException("시간 형식이 올바르지 않음");
            }
        }
    }

    private Integer parsePrice(String value) {
        try {
            Integer totalPrice = Integer.valueOf(value);
            if (totalPrice < 0) {
                throw new IllegalArgumentException("총 금액은 음수일 수 없음");
            }
            return totalPrice;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("총 금액 형식이 올바르지 않음");
        }
    }
}
