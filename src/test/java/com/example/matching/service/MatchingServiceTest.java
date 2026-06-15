package com.example.matching.service;

import com.example.matching.client.SpaceClient;
import com.example.matching.client.dto.SpaceMatchingContextResponse;
import com.example.matching.dto.request.MatchingCreateRequest;
import com.example.matching.entity.Matching;
import com.example.matching.entity.MatchingStatus;
import com.example.matching.repository.MatchingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchingServiceTest {

    @Mock
    private MatchingRepository matchingRepository;

    @Mock
    private SpaceClient spaceClient;

    private MatchingService matchingService;

    @BeforeEach
    void setUp() {
        matchingService = new MatchingService(matchingRepository, spaceClient);
    }

    @Test
    void createStoresSellerAndHostWhenSpaceIsAvailable() {
        MatchingCreateRequest request = createRequest();
        when(spaceClient.getMatchingContext(any(), any(), any()))
                .thenReturn(new SpaceMatchingContextResponse(1L, "host-1", true, true, true));
        when(matchingRepository.save(any(Matching.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        matchingService.create("seller-1", request);

        verify(matchingRepository).save(any(Matching.class));
    }

    @Test
    void createRejectsSellerOwnSpace() {
        when(spaceClient.getMatchingContext(any(), any(), any()))
                .thenReturn(new SpaceMatchingContextResponse(1L, "seller-1", true, true, true));

        assertThatThrownBy(() -> matchingService.create("seller-1", createRequest()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 공간에는 매칭을 요청할 수 없습니다.");

        verify(matchingRepository, never()).save(any());
    }

    @Test
    void receivedAndSentQueriesUseHostAndSeller() {
        when(matchingRepository.findByHostIdOrderByCreatedAtDesc("host-1"))
                .thenReturn(List.of());
        when(matchingRepository.findBySellerIdOrderByCreatedAtDesc("seller-1"))
                .thenReturn(List.of());

        matchingService.getReceivedMatchings("host-1");
        matchingService.getSentMatchings("seller-1");

        verify(matchingRepository).findByHostIdOrderByCreatedAtDesc("host-1");
        verify(matchingRepository).findBySellerIdOrderByCreatedAtDesc("seller-1");
    }

    @Test
    void approveRejectsOverlappingApprovedMatching() {
        Matching target = matching(
                LocalDateTime.of(2026, 6, 10, 10, 0),
                LocalDateTime.of(2026, 6, 10, 12, 0)
        );
        Matching approved = matching(
                LocalDateTime.of(2026, 6, 10, 11, 0),
                LocalDateTime.of(2026, 6, 10, 13, 0)
        );
        approved.approve("host-1");

        when(matchingRepository.findById(1L)).thenReturn(java.util.Optional.of(target));
        when(spaceClient.getMatchingContext(any(), any(), any()))
                .thenReturn(new SpaceMatchingContextResponse(1L, "host-1", true, true, true));
        when(matchingRepository.findAllBySpaceIdForUpdate(1L))
                .thenReturn(List.of(target, approved));

        assertThatThrownBy(() -> matchingService.approve("host-1", 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 승인된 매칭과 시간이 겹칩니다.");
        assertThat(target.getStatus()).isEqualTo(MatchingStatus.REQUESTED);
    }

    private MatchingCreateRequest createRequest() {
        return new MatchingCreateRequest(
                1L,
                "2026-06-10T10:00:00",
                "2026-06-10T12:00:00",
                "20000"
        );
    }

    private Matching matching(LocalDateTime startTime, LocalDateTime endTime) {
        return Matching.create(
                1L,
                "seller-1",
                "host-1",
                startTime,
                endTime,
                20000
        );
    }
}
