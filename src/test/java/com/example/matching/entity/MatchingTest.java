package com.example.matching.entity;

import com.example.matching.global.exception.ForbiddenException;
import com.example.matching.global.exception.InvalidMatchingStateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchingTest {

    private static final String SELLER_ID = "seller-1";
    private static final String HOST_ID = "host-1";

    @Test
    void hostCanApproveRequestedMatching() {
        Matching matching = createMatching();

        matching.approve(HOST_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.APPROVED);
    }

    @Test
    void hostCanRejectRequestedMatching() {
        Matching matching = createMatching();

        matching.reject(HOST_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.REJECTED);
    }

    @Test
    void sellerCanCancelRequestedMatching() {
        Matching matching = createMatching();

        matching.cancel(SELLER_ID);

        assertThat(matching.getStatus()).isEqualTo(MatchingStatus.CANCELED);
    }

    @Test
    void nonHostCannotApproveOrReject() {
        Matching matching = createMatching();

        assertThatThrownBy(() -> matching.approve("other-user"))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("매칭 처리 권한이 없습니다.");

        assertThatThrownBy(() -> matching.reject("other-user"))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("매칭 처리 권한이 없습니다.");
    }

    @Test
    void nonSellerCannotCancel() {
        Matching matching = createMatching();

        assertThatThrownBy(() -> matching.cancel(HOST_ID))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("매칭 취소 권한이 없습니다.");
    }

    @Test
    void processedMatchingCannotChangeStateAgain() {
        Matching matching = createMatching();
        matching.approve(HOST_ID);

        assertThatThrownBy(() -> matching.reject(HOST_ID))
                .isInstanceOf(InvalidMatchingStateException.class)
                .hasMessage("요청 상태의 매칭만 처리 가능");
    }

    private Matching createMatching() {
        return Matching.create(
                1L,
                SELLER_ID,
                HOST_ID,
                LocalDateTime.of(2026, 6, 10, 10, 0),
                LocalDateTime.of(2026, 6, 10, 11, 0),
                10000
        );
    }
}
