package com.example.matchings.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchingTest {

    private static final String REQUESTER_ID = "requester-1";
    private static final String SPACE_OWNER_ID = "space-owner-1";

    @Test
    void approveChangesRequestedMatchingToApproved() {
        Matching matching = createMatching();

        matching.approve(SPACE_OWNER_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.APPROVED);
    }

    @Test
    void rejectChangesRequestedMatchingToRejected() {
        Matching matching = createMatching();

        matching.reject(SPACE_OWNER_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.REJECTED);
    }

    @Test
    void approveThrowsWhenRequesterTriesToApproveOwnMatching() {
        Matching matching = createMatching();

        assertThatThrownBy(() -> matching.approve(REQUESTER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("매칭 처리 권한 없음");
    }

    @Test
    void rejectThrowsWhenRequesterTriesToRejectOwnMatching() {
        Matching matching = createMatching();

        assertThatThrownBy(() -> matching.reject(REQUESTER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("매칭 처리 권한 없음");
    }

    @Test
    void approveThrowsWhenMatchingIsNotRequested() {
        Matching approvedMatching = createMatching();
        approvedMatching.approve(SPACE_OWNER_ID);

        assertThatThrownBy(() -> approvedMatching.approve(SPACE_OWNER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");

        Matching rejectedMatching = createMatching();
        rejectedMatching.reject(SPACE_OWNER_ID);

        assertThatThrownBy(() -> rejectedMatching.approve(SPACE_OWNER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");
    }

    @Test
    void rejectThrowsWhenMatchingIsNotRequested() {
        Matching approvedMatching = createMatching();
        approvedMatching.approve(SPACE_OWNER_ID);

        assertThatThrownBy(() -> approvedMatching.reject(SPACE_OWNER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");

        Matching rejectedMatching = createMatching();
        rejectedMatching.reject(SPACE_OWNER_ID);

        assertThatThrownBy(() -> rejectedMatching.reject(SPACE_OWNER_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");
    }

    private Matching createMatching() {
        return Matching.create(
                1L,
                REQUESTER_ID,
                LocalDateTime.of(2026, 6, 10, 10, 0),
                LocalDateTime.of(2026, 6, 10, 11, 0),
                10000
        );
    }
}
