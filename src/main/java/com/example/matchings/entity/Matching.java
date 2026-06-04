package com.example.matchings.entity;

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

    @Column(nullable = false, name = "seller_id")
    private String sellerId;

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
            String sellerId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer totalPrice
    ) {
        return Matching.builder()
                .spaceId(spaceId)
                .sellerId(sellerId)
                .startTime(startTime)
                .endTime(endTime)
                .totalPrice(totalPrice)
                .status(MatchingStatus.REQUESTED)
                .build();
    }

    public void approve(String userId) {
        validateSeller(userId);
        this.status = MatchingStatus.APPROVED;
    }

    public void reject(String userId) {
        validateSeller(userId);
        this.status = MatchingStatus.REJECTED;
    }

    private void validateSeller(String userId) {
        if (!sellerId.equals(userId)) {
            throw new IllegalStateException("매칭 처리 권한 없음");
        }
    }
}
