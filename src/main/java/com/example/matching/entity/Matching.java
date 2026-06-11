package com.example.matching.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "matchings")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "space_id")
    private Long spaceId;

    @Column(nullable = false, name = "requester_id")
    private String requesterId;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    @Column(nullable = false, name = "total_price")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchingStatus status;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Matching create(
            Long spaceId,
            String requesterId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer totalPrice
    ) {
        return Matching.builder()
                .spaceId(spaceId)
                .requesterId(requesterId)
                .startTime(startTime)
                .endTime(endTime)
                .totalPrice(totalPrice)
                .status(MatchingStatus.REQUESTED)
                .build();
    }

    public void approve(String userId) {
        validateNotRequester(userId);
        validateRequested();
        this.status = MatchingStatus.APPROVED;
    }

    public void reject(String userId) {
        validateNotRequester(userId);
        validateRequested();
        this.status = MatchingStatus.REJECTED;
    }

    private void validateNotRequester(String userId) {
        if (requesterId.equals(userId)) {
            throw new IllegalStateException("매칭 처리 권한 없음");
        }
    }

    private void validateRequested() {
        if (status != MatchingStatus.REQUESTED) {
            throw new IllegalStateException("요청 상태의 매칭만 처리 가능");
        }
    }
}
