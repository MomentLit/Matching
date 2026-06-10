package com.example.matchings.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchingTest {

    private static final String SELLER_ID = "seller-1";

    @Test
    void approveChangesRequestedMatchingToApproved() {
        Matching matching = createMatching();

        matching.approve(SELLER_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.APPROVED);
    }

    @Test
    void rejectChangesRequestedMatchingToRejected() {
        Matching matching = createMatching();

        matching.reject(SELLER_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.REJECTED);
    }

    @Test
    void approveThrowsWhenMatchingIsNotRequested() {
        Matching approvedMatching = createMatching();
        approvedMatching.approve(SELLER_ID);

        assertThatThrownBy(() -> approvedMatching.approve(SELLER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");

        Matching rejectedMatching = createMatching();
        rejectedMatching.reject(SELLER_ID);

        assertThatThrownBy(() -> rejectedMatching.approve(SELLER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");
    }

    @Test
    void rejectThrowsWhenMatchingIsNotRequested() {
        Matching approvedMatching = createMatching();
        approvedMatching.approve(SELLER_ID);

        assertThatThrownBy(() -> approvedMatching.reject(SELLER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");

        Matching rejectedMatching = createMatching();
        rejectedMatching.reject(SELLER_ID);

        assertThatThrownBy(() -> rejectedMatching.reject(SELLER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");
    }

    private Matching createMatching() {
        return Matching.create(
                1L,
                SELLER_ID,
                LocalDateTime.of(2026, 6, 10, 10, 0),
                LocalDateTime.of(2026, 6, 10, 11, 0),
                10000
